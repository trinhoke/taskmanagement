package com.team11.taskmanagement.dto.task;

import java.util.List;
import java.time.LocalDate;
import lombok.Data;

@Data
public class TaskDTO {
    private String name;
    private String description; 
    private LocalDate dueDate;
    private List<Long> assigneeIds;
}
