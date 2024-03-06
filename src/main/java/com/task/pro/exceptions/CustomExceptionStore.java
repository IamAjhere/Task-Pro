package com.task.pro.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CustomExceptionStore {
    USER_EXISTS("USER_EXISTS", "User Already Exists", HttpStatus.CONFLICT.value()),
    USER_NOT_FOUND("USER_NOT_EXISTS", "User not Found.", HttpStatus.NOT_FOUND.value()),
    INVALID_LOGIN("INVALID_LOGIN_CREDENTIALS", "Invalid Email or Password", HttpStatus.NOT_FOUND.value());

    private final String code;
    private final String message;
    private final int statusCode;

    CustomExceptionStore(String code, String message, int statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

}

