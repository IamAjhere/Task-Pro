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
            User user1 = User.builder()
                    .firstName("John")
                    .lastName("Doe")
                    .email("john.doe@example.com")
                    .password("password")
                    .createdAt(LocalDateTime.now().minusDays(3))
                    .updatedAt(LocalDateTime.now().minusDays(2))
                    .build();

            User user2 = User.builder()
                    .firstName("Jane")
                    .lastName("Doe")
                    .email("jane.doe@example.com")
                    .password("password")
                    .createdAt(LocalDateTime.now().minusDays(5))
                    .updatedAt(LocalDateTime.now().minusDays(4))
                    .build();

            userRepository.saveAll(List.of(user1, user2));

            Task task1 = Task.builder()
                    .taskName("Task 1")
                    .taskPriority(TaskPriority.HIGH)
                    .createdAt(LocalDateTime.now().minusDays(3))
                    .updatedAt(LocalDateTime.now().minusDays(2))
                    .createdBy(user1)
                    .dueDate(LocalDateTime.now().plusDays(7))
                    .markedAsComplete(false)
                    .build();

            Task task2 = Task.builder()
                    .taskName("Task 2")
                    .taskPriority(TaskPriority.MEDIUM)
                    .createdAt(LocalDateTime.now().minusDays(5))
                    .updatedAt(LocalDateTime.now().minusDays(4))
                    .createdBy(user2)
                    .dueDate(LocalDateTime.now().plusDays(5))
                    .markedAsComplete(true)
                    .build();

            Task task3 = Task.builder()
                    .taskName("Task 3")
                    .taskPriority(TaskPriority.LOW)
                    .createdAt(LocalDateTime.now().minusDays(5))
                    .updatedAt(LocalDateTime.now().minusDays(4))
                    .createdBy(user2)
                    .dueDate(LocalDateTime.now().plusDays(5))
                    .markedAsComplete(true)
                    .build();

            taskRepository.saveAll(List.of(task1, task2, task3));
        };
    }
}
