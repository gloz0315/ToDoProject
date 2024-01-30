package com.miniproject.todoproject.dto;

import com.miniproject.todoproject.dto.tododto.ToDoResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class UsersToDoResponseDto {
    private String username;
    private List<ToDoResponse> todoList = new ArrayList<>();

    public UsersToDoResponseDto(String username, List<ToDoResponse> responseList) {
        this.username = username;
        this.todoList = responseList;
    }
}
