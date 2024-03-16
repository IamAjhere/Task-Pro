package com.task.pro.task;

import com.task.pro.user.UserDTO;

import java.time.LocalDateTime;

public record TaskDTO(
        Long id,
        String taskName,
        TaskPriority taskPriority,
        LocalDateTime dueDate,
        Boolean markedAsComplete,
        UserDTO createdBy
) {
}
