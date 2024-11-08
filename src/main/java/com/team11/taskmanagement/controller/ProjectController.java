package com.team11.taskmanagement.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.team11.taskmanagement.dto.ProjectDTO;
import com.team11.taskmanagement.exception.ResourceNotFoundException;
import com.team11.taskmanagement.model.Project;
import com.team11.taskmanagement.model.ProjectAnnouncement;
import com.team11.taskmanagement.model.Task;
import com.team11.taskmanagement.model.User;
import com.team11.taskmanagement.service.ProjectAnnouncementService;
import com.team11.taskmanagement.service.ProjectService;
import com.team11.taskmanagement.service.TaskService;
import com.team11.taskmanagement.service.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/projects")
@Slf4j
public class ProjectController {

    private final ProjectService projectService;
    private final UserService userService;
    private final TaskService taskService;
    private final ProjectAnnouncementService announcementService;
    
    public ProjectController(ProjectService projectService, UserService userService, TaskService taskService, ProjectAnnouncementService announcementService) {
        this.projectService = projectService;
        this.userService = userService;
        this.taskService = taskService;
        this.announcementService = announcementService;
    }

    // Show list projects
    @GetMapping
    public String listProjects(Model model) {
        log.info("Show list projects");
        model.addAttribute("currentUrl", "/projects");
        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        return "projects/index"; 
    }

    // Show create project form
    @GetMapping("/create")
    public String showCreateProjectForm(Model model) {
        log.info("Show create project form");
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("project", new Project());
        return "projects/project-create";
    }

    // Process create new project
    @PostMapping("/create")
    public String createProject(@Valid @ModelAttribute("project") ProjectDTO projectDTO, 
                                BindingResult result, 
                                RedirectAttributes redirectAttributes) {
        log.info("Process create new project: {}", projectDTO);
        if (result.hasErrors()) {
            return "projects/project-create";
        }
        try {
            Project createdProject = projectService.createProject(projectDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Project created successfully");
            return "redirect:/projects/" + createdProject.getId();
        } catch (IllegalArgumentException e) {
            log.error("Error when create project", e);
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/projects/create";
        }
    }

    // Show project details
    @GetMapping("/{id}")
    public String showProjectDetails(@PathVariable Long id, Model model) {
        log.info("Show project details with ID: {}", id);
        try {
            Project project = projectService.getProjectById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + id));
            
            List<Task> tasks = taskService.getTasksByProjectId(id);
            
            List<User> members = userService.getUsersByProjectId(id);

            List<ProjectAnnouncement> announcements = 
            announcementService.getAnnouncementsByProject(project);
            
            model.addAttribute("project", project);
            model.addAttribute("tasks", tasks);
            model.addAttribute("members", members);
            model.addAttribute("announcements", announcements);
            return "projects/project-details";
        } catch (ResourceNotFoundException e) {
            log.error("Project not found", e);
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    // Process create announcement
    @PostMapping("/{id}/announcements")
        public String createAnnouncement(@PathVariable Long id,
                                    @RequestParam String content,
                                    @RequestParam String title,
                                    @AuthenticationPrincipal UserDetails userDetails,
                                    RedirectAttributes redirectAttributes) {
            try {
                Project project = projectService.getProjectById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy dự án"));
                
                User currentUser = userService.findByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

                announcementService.createAnnouncement(project, currentUser, title, content);
                redirectAttributes.addFlashAttribute("successMessage", "Đã tạo thông báo thành công!");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra khi tạo thông báo: " + e.getMessage());
            }
            
            return "redirect:/projects/" + id;
        }
    
    // Show trash project
    @GetMapping("/trash")
    public String showTrash(Model model) {
        log.info("Show trash project");
        model.addAttribute("currentUrl", "/projects/trash");
        // Add logic to get list of deleted projects if needed
        return "projects/trash";
    }
}
