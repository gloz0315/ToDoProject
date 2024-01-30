package com.miniproject.todoproject.controller;

import com.miniproject.todoproject.dto.ResponseDto;
import com.miniproject.todoproject.dto.ToDoRequest;
import com.miniproject.todoproject.dto.ToDoResponse;
import com.miniproject.todoproject.security.UserDetailsImpl;
import com.miniproject.todoproject.security.UserDetailsServiceImpl;
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

    @PostMapping("/todo")
    public ToDoResponse createToDo(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ToDoRequest request) {
        return todoService.createTodo(userDetails.getUser(),request);
    }

    // 할일 목록 가져오기
    @GetMapping("/todo")
    public List<ToDoResponse> getToDoList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return todoService.getToDoList(userDetails.getUser());
    }
}
