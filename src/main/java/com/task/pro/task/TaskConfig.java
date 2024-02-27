// TaskConfig.java

package com.task.pro.task;

import com.task.pro.user.User;
import com.task.pro.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class TaskConfig {

    @Bean
    CommandLineRunner taskCommandLineRunner(TaskRepository taskRepository, UserRepository userRepository) {
        return args -> {
            User user1 = new User(
                    "John",
                    "Doe",
                    "john.doe@example.com",
                    "password",
                    LocalDateTime.now().minusDays(3),
                    LocalDateTime.now().minusDays(2)
            );

            User user2 = new User(
                    "Jane",
                    "Doe",
                    "jane.doe@example.com",
                    "password",
                    LocalDateTime.now().minusDays(5),
                    LocalDateTime.now().minusDays(4)
            );

            userRepository.saveAll(
                    List.of(user1, user2)
            );

            Task task1 = new Task(
                    "Task 1",
                    Task.TaskPriority.HIGH,
                    LocalDateTime.now().minusDays(3),
                    LocalDateTime.now().minusDays(2),
                    user1,
                    LocalDateTime.now().plusDays(7),
                    false
            );

            Task task2 = new Task(
                    "Task 2",
                    Task.TaskPriority.MEDIUM,
                    LocalDateTime.now().minusDays(5),
                    LocalDateTime.now().minusDays(4),
                    user2,
                    LocalDateTime.now().plusDays(5),
                    true
            );
            Task task3 = new Task(
                    "Task 3",
                    Task.TaskPriority.LOW,
                    LocalDateTime.now().minusDays(5),
                    LocalDateTime.now().minusDays(4),
                    user2,
                    LocalDateTime.now().plusDays(5),
                    true
            );

            taskRepository.saveAll(
                    List.of(task1, task2,task3)
            );
        };
    }
}
