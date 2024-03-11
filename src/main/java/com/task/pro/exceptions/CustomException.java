package com.task.pro.exceptions;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final String code;
    private final String message;
    private final int statusCode;

    public CustomException(CustomExceptionStore customExceptionStore) {
        this.code = customExceptionStore.getCode();
        this.message = customExceptionStore.getMessage();
        this.statusCode = customExceptionStore.getStatusCode();
    }
}