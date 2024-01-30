package com.miniproject.todoproject.dto;

import com.miniproject.todoproject.entity.Todo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ToDoResponse {
    private String title;
    private String contents;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public ToDoResponse(Todo todo) {
        this.title = todo.getTitle();
        this.contents = todo.getContents();
        this.createAt = todo.getCreateAt();
        this.modifiedAt = todo.getModifiedAt();
    }
}
