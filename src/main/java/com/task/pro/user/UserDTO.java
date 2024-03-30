package com.task.pro.user;

public record UserDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        Role role

){
}
