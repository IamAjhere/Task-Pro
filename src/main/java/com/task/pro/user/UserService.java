package com.task.pro.user;

import com.task.pro.exceptions.CustomException;
import com.task.pro.exceptions.CustomExceptionStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class UserService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;
    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(CustomExceptionStore.USER_NOT_FOUND));
    }
}
