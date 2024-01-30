package com.miniproject.todoproject.controller;

import com.miniproject.todoproject.dto.tododto.ToDoReadResponseDto;
import com.miniproject.todoproject.dto.tododto.ToDoRequestDto;
import com.miniproject.todoproject.dto.tododto.ToDoResponseDto;
import com.miniproject.todoproject.dto.usersDto.UsersToDoResponseDto;
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

    // 할 일 카드 작성 기능
    @PostMapping("/todo")
    public ToDoResponseDto createToDo(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ToDoRequestDto request) {
        return todoService.createTodo(userDetails.getUser(),request);
    }

    // 전체 할일 목록 가져오기
    @GetMapping("/todo")
    public List<UsersToDoResponseDto> getToDoList() {
        return todoService.getToDoList();
    }

    // 할일 카드 조회 기능
    @GetMapping("/todo/{id}")
    public ToDoReadResponseDto readToDo(@PathVariable("id") Long id) {
        return todoService.readToDo(id);
    }

    // 할일 카드 수정 (당사자만 가능)
    @PutMapping("/todo/{id}")
    public ToDoReadResponseDto updateToDo(@PathVariable("id") Long id,@AuthenticationPrincipal UserDetailsImpl userDetails,
                                      @RequestBody ToDoRequestDto request) {
        return todoService.updateTodo(id,userDetails.getUser(),request);
    }

    // 할일 카드 완료 기능 (당사자만 가능)
    @PatchMapping("/todo/{id}")
    public ToDoResponseDto completeTodo(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return todoService.completeTodo(id,userDetails.getUser());
    }
}
