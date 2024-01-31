package com.miniproject.todoproject.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject.todoproject.dto.ResponseDto;
import com.miniproject.todoproject.dto.tododto.ToDoReadResponseDto;
import com.miniproject.todoproject.dto.tododto.ToDoRequestDto;
import com.miniproject.todoproject.dto.tododto.ToDoResponseDto;
import com.miniproject.todoproject.dto.tododto.TodoCommentResponseDto;
import com.miniproject.todoproject.dto.usersdto.UsersToDoResponseDto;
import com.miniproject.todoproject.security.UserDetailsImpl;
import com.miniproject.todoproject.service.TodoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TodoController {

	private final TodoService todoService;

	// 할 일 카드 작성 기능
	@PostMapping("/todo")
	public ResponseEntity<ResponseDto<ToDoResponseDto>> createToDo(@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody ToDoRequestDto request) {
		return todoService.createTodo(userDetails.getUser(), request);
	}

	// 전체 할일 목록 가져오기 (작성일 기준으로 내림차순)
	@GetMapping("/todo")
	public ResponseEntity<ResponseDto<List<UsersToDoResponseDto>>> getToDoList() {
		return todoService.getToDoList();
	}

	// 할일 카드 조회 기능 (댓글 포함해서 읽기?) -> 그전까지는 문제가없음
	@GetMapping("/todo/{id}")
	public ResponseEntity<ResponseDto<TodoCommentResponseDto>> readToDo(@PathVariable("id") Long id) {
		return todoService.readToDo(id);
	}

	// 할일 카드 수정 (당사자만 가능)
	@PutMapping("/todo/{id}")
	public ResponseEntity<ResponseDto<ToDoReadResponseDto>> updateToDo(@PathVariable("id") Long id,
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody ToDoRequestDto request) {
		return todoService.updateTodo(id, userDetails.getUser(), request);
	}

	// 할일 카드 완료 기능 (당사자만 가능)
	@PatchMapping("/todo/{id}")
	public ResponseEntity<ResponseDto<ToDoResponseDto>> completeTodo(@PathVariable("id") Long id,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return todoService.completeTodo(id, userDetails.getUser());
	}
}
