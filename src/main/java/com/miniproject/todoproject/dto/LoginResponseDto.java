package com.miniproject.todoproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class LoginResponseDto {
    private HttpStatus status;
    private String message;
}
