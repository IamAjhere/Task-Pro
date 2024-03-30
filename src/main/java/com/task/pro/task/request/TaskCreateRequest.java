package com.task.pro.task.request;

import com.task.pro.task.TaskPriority;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskCreateRequest {

    @NotBlank(message = "Task name cannot be blank")
    private String taskName;

    private TaskPriority taskPriority;

    @Future(message = "Task due date cannot be in the past")
    private LocalDateTime dueDate;
}
