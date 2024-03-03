package com.task.pro.task;

import com.task.pro.config.SecretConfiguration;
import com.task.pro.user.Role;
import com.task.pro.user.User;
import com.task.pro.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class TaskConfig {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    @Bean
    CommandLineRunner taskCommandLineRunner(TaskRepository taskRepository, UserRepository userRepository) {

        return args -> {
            User user1 = User.builder()
                    .firstName("Super")
                    .lastName("Admin")
                    .email("admin@admin.com")
                    .password("$2a$10$rSkC3mBny./IsY5rN.QOeOdFeOc4.xbAEcfWGvqFnETJpta5sLqXK")
                    .role(Role.INDIVIDUAL)
                    .createdAt(LocalDateTime.now().minusDays(3))
                    .updatedAt(LocalDateTime.now().minusDays(2))
                    .build();

            userRepository.saveAll(List.of(user1));

            Task task1 = Task.builder()
                    .taskName("Task 1")
                    .taskPriority(TaskPriority.HIGH)
                    .createdAt(LocalDateTime.now().minusDays(3))
                    .updatedAt(LocalDateTime.now().minusDays(2))
                    .createdBy(user1)
                    .dueDate(LocalDateTime.now().plusDays(7))
                    .markedAsComplete(false)
                    .build();


            taskRepository.saveAll(List.of(task1));
        };
    }
}
