package com.miniproject.todoproject.controller;

import com.miniproject.todoproject.dto.tododto.ToDoRequest;
import com.miniproject.todoproject.dto.tododto.ToDoResponse;
import com.miniproject.todoproject.dto.UsersToDoResponseDto;
import com.miniproject.todoproject.security.UserDetailsImpl;
import com.miniproject.todoproject.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TodoController {

    private final TodoService todoService;

    // 할 일 생성하기
    @PostMapping("/todo")
    public ToDoResponse createToDo(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ToDoRequest request) {
        return todoService.createTodo(userDetails.getUser(),request);
    }

    // 전체 할일 목록 가져오기
    @GetMapping("/todo")
    public List<UsersToDoResponseDto> getToDoList() {
        return todoService.getToDoList();
    }
}
