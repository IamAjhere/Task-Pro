package com.task.pro.task;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    public List<Task> getTasks() {
        return List.of(
                new Task(
                        1L,
                        "Task 1",
                        Task.TaskPriority.HIGH,
                        LocalDateTime.now().minusDays(3),
                        LocalDateTime.now().minusDays(2),
                        "John Doe",
                        LocalDateTime.now().plusDays(7),
                        false
                ),
                new Task(
                        2L,
                        "Task 2",
                        Task.TaskPriority.MEDIUM,
                        LocalDateTime.now().minusDays(5),
                        LocalDateTime.now().minusDays(4),
                        "Jane Smith",
                        LocalDateTime.now().plusDays(5),
                        true
                )

        );
    }
}
