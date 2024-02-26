package com.task.pro.task;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class TaskConfig {

    @Bean
    CommandLineRunner commandLineRunner (TaskRepository taskRepository){
        return args -> {
            Task Task1 = new Task(
                    "Task 1",
                    Task.TaskPriority.HIGH,
                    LocalDateTime.now().minusDays(3),
                    LocalDateTime.now().minusDays(2),
                    "John Doe",
                    LocalDateTime.now().plusDays(7),
                    false
            );
            Task Task2 = new Task(
                    "Task 2",
                    Task.TaskPriority.MEDIUM,
                    LocalDateTime.now().minusDays(5),
                    LocalDateTime.now().minusDays(4),
                    "John Doe",
                    LocalDateTime.now().plusDays(5),
                    true
            );
            taskRepository.saveAll(
                    List.of(Task1, Task2)
            );
        };
    }
}
