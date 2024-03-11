package com.task.pro.user;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserRepositoryTest {
     @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    public void userRepository_SaveUser_ReturnSavedUser() {
        User testUser = User.builder()
                .firstName("Test")
                .lastName("User")
                .email("Test@test.com")
                .password("$2a$10$rSkC3mBny./IsY5rN.QOeOdFeOc4.xbAEcfWGvqFnETJpta5sLqXK")
                .role(Role.INDIVIDUAL)
                .createdAt(LocalDateTime.now().minusDays(5))
                .updatedAt(LocalDateTime.now().minusDays(4))
                .build();

        User savedUser = userRepository.save(testUser);

        assertNotNull(savedUser, "User is NULL");
        assertNotNull(savedUser.getId(), "User ID is NULL");

    }
}
