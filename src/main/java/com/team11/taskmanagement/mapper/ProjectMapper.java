package com.team11.taskmanagement.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import com.team11.taskmanagement.model.ProjectStatus;
import com.team11.taskmanagement.model.ProjectPriority;
import com.team11.taskmanagement.model.TagProject;
import com.team11.taskmanagement.dto.ProjectDTO;
import com.team11.taskmanagement.dto.project.ProjectResponseDTO;
import com.team11.taskmanagement.dto.project.ProjectUpdateDTO;
import com.team11.taskmanagement.model.Project;


@Mapper(
    componentModel = "spring",
    uses = {UserMapper.class, TaskMapper.class},
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ProjectMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "members", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "status", expression = "java(com.team11.taskmanagement.model.ProjectStatus.valueOf(dto.getStatus()))")
    @Mapping(target = "priority", expression = "java(com.team11.taskmanagement.model.ProjectPriority.valueOf(dto.getPriority()))")
    @Mapping(target = "tag", expression = "java(com.team11.taskmanagement.model.TagProject.valueOf(dto.getTag()))")
    Project toEntity(ProjectDTO dto);

    @Mapping(target = "members", source = "members")
    @Mapping(target = "tasks", source = "tasks")
    ProjectResponseDTO toResponseDTO(Project project);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "members", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "name", source = "basic.name")
    @Mapping(target = "description", source = "basic.description")
    @Mapping(target = "startDate", source = "basic.startDate")
    @Mapping(target = "dueDate", source = "basic.dueDate")
    @Mapping(target = "status", expression = "java(dto.getBasic() != null && dto.getBasic().getStatus() != null ? com.team11.taskmanagement.model.ProjectStatus.valueOf(dto.getBasic().getStatus()) : project.getStatus())")
    @Mapping(target = "priority", expression = "java(dto.getBasic() != null && dto.getBasic().getPriority() != null ? com.team11.taskmanagement.model.ProjectPriority.valueOf(dto.getBasic().getPriority()) : project.getPriority())")
    @Mapping(target = "tag", expression = "java(dto.getBasic() != null && dto.getBasic().getTag() != null ? com.team11.taskmanagement.model.TagProject.valueOf(dto.getBasic().getTag()) : project.getTag())")
    @Mapping(target = "announcements", ignore = true)
    void updateEntityFromDTO(ProjectUpdateDTO dto, @MappingTarget Project project);
} 