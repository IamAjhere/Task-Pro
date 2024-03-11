package com.task.pro.exceptions;

public record ErrorResponse(String code, String message, int statusCode) {
}

