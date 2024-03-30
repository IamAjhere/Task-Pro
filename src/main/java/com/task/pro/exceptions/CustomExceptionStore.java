package com.task.pro.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CustomExceptionStore {
    // User Permissions
    USER_EXISTS("USER_EXISTS", "User Already Exists", HttpStatus.CONFLICT.value()),
    USER_NOT_FOUND("USER_NOT_EXISTS", "User not Found.", HttpStatus.NOT_FOUND.value()),
    INVALID_LOGIN("INVALID_LOGIN_CREDENTIALS", "Invalid Email or Password", HttpStatus.NOT_FOUND.value()),
    NO_PERMISSION("INVALID_PERMISSION", "You Don't Have Permission to Access", HttpStatus.UNAUTHORIZED.value()),
    TEAM_MEMBER_NOT_ALLOWED("TEAM_MEMBER_NOT_ALLOWED", "Team Member is Not Allowed.", HttpStatus.UNAUTHORIZED.value()),
    AUTHORIZATION_FAILED("AUTHORIZATION_FAILED", "INVALID AUTHORIZATION",HttpStatus.UNAUTHORIZED.value()),

    // Task
    TASK_NOT_FOUND("TASK_NOT_FOUND","Task not found." , HttpStatus.NOT_FOUND.value()),
    TASK_CREATION_FAILED("TASK_CREATION_FAILED", "Task creation failed", HttpStatus.INTERNAL_SERVER_ERROR.value()),
    TASK_DELETION_FAILED("TASK_DELETION_FAILED", "Task deletion failed", HttpStatus.INTERNAL_SERVER_ERROR.value()),
    TASK_UPDATE_FAILED("TASK_UPDATE_FAILED","Task Update Failed" , HttpStatus.INTERNAL_SERVER_ERROR.value());


    private final String code;
    private final String message;
    private final int statusCode;

    CustomExceptionStore(String code, String message, int statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
    public static String getMessage(String code) {
        for (CustomExceptionStore exception : values()) {
            if (exception.code.equals(code)) {
                return exception.message;
            }
        }
        return null;
    }

    public static String getCode(String message) {
        for (CustomExceptionStore exception : values()) {
            if (exception.message.equals(message)) {
                return exception.code;
            }
        }
        return null;
    }

    public static int getStatusCode(String code) {
        for (CustomExceptionStore exception : values()) {
            if (exception.code.equals(code)) {
                return exception.statusCode;
            }
        }
        return 0;
    }
}

