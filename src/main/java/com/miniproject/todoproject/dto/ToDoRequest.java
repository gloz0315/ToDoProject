package com.miniproject.todoproject.dto;

import com.miniproject.todoproject.entity.User;
import lombok.Getter;

@Getter
public class ToDoRequest {
    private String title;
    private String contents;
}
