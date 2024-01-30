package com.miniproject.todoproject.dto.tododto;

import com.miniproject.todoproject.entity.User;
import lombok.Getter;

@Getter
public class ToDoRequest {
    private String title;
    private String contents;
}
