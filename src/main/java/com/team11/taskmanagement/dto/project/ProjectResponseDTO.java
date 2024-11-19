package com.team11.taskmanagement.dto.project;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import com.team11.taskmanagement.dto.task.TaskResponseDTO;
import com.team11.taskmanagement.dto.user.UserSummaryDTO;
import com.team11.taskmanagement.model.ProjectPriority;
import com.team11.taskmanagement.model.ProjectStatus;
import com.team11.taskmanagement.model.TagProject;

import lombok.Data;

@Data
public class ProjectResponseDTO {
    private Long id;
    private String name;
    private String description;
    private ProjectStatus status;
    private LocalDate startDate;
    private LocalDate dueDate;
    private ProjectPriority priority;
    private TagProject tag;
    private Set<UserSummaryDTO> members;
    private Set<TaskResponseDTO> tasks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}