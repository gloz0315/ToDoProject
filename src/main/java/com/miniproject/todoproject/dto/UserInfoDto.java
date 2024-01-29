package com.miniproject.todoproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class UserInfoDto {
    private String username;
    private boolean isAdmin;
}
