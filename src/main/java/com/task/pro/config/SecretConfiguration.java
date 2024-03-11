package com.task.pro.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("secret")
public record SecretConfiguration(String JWTSecret) {
}
