package com.team11.taskmanagement.dto.task;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class TaskCreateDTO {
    private String name;
    private String description; 
    private LocalDate dueDate;
    private List<Long> assigneeIds;
}
