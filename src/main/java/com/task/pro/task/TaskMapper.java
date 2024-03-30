package com.task.pro.task;

import com.task.pro.user.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface TaskMapper {

    @Mapping(target = "createdBy", source = "createdBy")
    TaskDTO toTaskDTO(Task task);

    List<TaskDTO> toTaskDTOList(List<Task> tasks);
}