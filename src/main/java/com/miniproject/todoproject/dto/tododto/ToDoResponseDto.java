package com.miniproject.todoproject.dto.tododto;

import com.miniproject.todoproject.entity.Todo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ToDoResponseDto {
    private String title;
    private String contents;
    private LocalDateTime createAt;
    private boolean complete;

    public ToDoResponseDto(Todo todo) {
        this.title = todo.getTitle();
        this.contents = todo.getContents();
        this.createAt = todo.getCreateAt();
        this.complete = todo.isComplete();
    }
}
