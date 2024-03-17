package com.task.pro.task.request;

import com.task.pro.task.TaskPriority;
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

    @NotBlank(message = "Task Priority cannot be blank")
    private TaskPriority taskPriority;

    @NotBlank(message = "Task Priority cannot be blank")
    private LocalDateTime dueDate;
}
