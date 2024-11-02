package com.team11.taskmanagement.controller.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.team11.taskmanagement.model.Task;
import com.team11.taskmanagement.service.TaskService;

import jakarta.validation.Valid;

import com.team11.taskmanagement.dto.task.TaskCreateDTO;
import com.team11.taskmanagement.dto.task.TaskResponseDTO;

@RestController
@RequestMapping("/api/projects")
public class ProjectApiController {

    private final TaskService taskService;

    public ProjectApiController(TaskService taskService) {
        this.taskService = taskService;   
    }
    
    @PostMapping("/{id}/add-task")
    public ResponseEntity<TaskResponseDTO> createTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskCreateDTO createDTO) {
        TaskResponseDTO response = taskService.createTask(id, createDTO);
        return ResponseEntity.ok(response);
    }


}
