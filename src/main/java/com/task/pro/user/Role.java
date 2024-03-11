package com.task.pro.user;

import jakarta.validation.constraints.NotBlank;

public enum Role {
    @NotBlank
    INDIVIDUAL,
    @NotBlank
    TEAM_OWNER,
    @NotBlank
    TEAM_MEMBER
}
