package com.miniproject.todoproject.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    @NotNull
    private String username;
    @NotNull
    private String password;
    private boolean admin = false;
    private String adminToken = "";
}
