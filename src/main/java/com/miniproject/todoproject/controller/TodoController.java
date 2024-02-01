package com.miniproject.todoproject.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Todo 컨트롤러", description = "할 일 카드에 관한 API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TodoController {

	private final TodoService todoService;

	// 할 일 카드 작성 기능
	@Operation(summary = "할 일 카드 작성", description = "카드를 생성할 수 있습니다.")
	@ApiResponse(responseCode = "201", description = "할 일 카드를 생성하셨습니다.",
		content = @Content(schema = @Schema(implementation = ToDoResponseDto.class)))
	@PostMapping("/todo")
	public ResponseEntity<ResponseDto<ToDoResponseDto>> createTodo(@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody ToDoRequestDto request) {
		return todoService.createTodo(userDetails.getUser(), request);
	}

	// 전체 할일 목록 가져오기 (작성일 기준으로 내림차순)
	@Operation(summary = "할 일 카드 전체 목록 조회", description = "할 일 카드 전체를 작성일 기준으로 내림차순 및 완료에 따라 정렬됩니다.")
	@ApiResponse(responseCode = "201", description = "카드 목록들을 조회합니다.",
		content = @Content(schema = @Schema(implementation = UsersToDoResponseDto.class)))
	@GetMapping("/todo")
	public ResponseEntity<ResponseDto<List<UsersToDoResponseDto>>> getTodoList() {
		return todoService.getToDoList();
	}

	// 할일 카드 조회 기능
	@Operation(summary = "할 일 카드 조회", description = "해당 카드의 정보를 조회합니다.")
	@ApiResponse(responseCode = "200", description = "카드 정보를 조회합니다.",
		content = @Content(schema = @Schema(implementation = TodoCommentResponseDto.class)))
	@GetMapping("/todo/{id}")
	public ResponseEntity<ResponseDto<TodoCommentResponseDto>> readTodo(@PathVariable("id") Long id) {
		return todoService.readToDo(id);
	}

	// 할일 카드 수정 (당사자만 가능)
	@Operation(summary = "카드 내용 수정", description = "해당 카드의 정보를 수정할 수 있습니다.")
	@ApiResponse(responseCode = "200", description = "카드를 수정합니다.",
		content = @Content(schema = @Schema(implementation = ResponseDto.class)))
	@PutMapping("/todo/{id}")
	public ResponseEntity<ResponseDto<ToDoReadResponseDto>> updateTodo(@PathVariable("id") Long id,
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody ToDoRequestDto request) {
		return todoService.updateTodo(id, userDetails.getUser(), request);
	}

	// 할일 카드 완료 기능 (당사자만 가능)
	@Operation(summary = "할 일 카드 완료", description = "당사자가 카드를 완료할 수 있습니다.")
	@ApiResponse(responseCode = "200", description = "카드를 완료하였습니다.",
		content = @Content(schema = @Schema(implementation = ResponseDto.class)))
	@PatchMapping("/todo/{id}")
	public ResponseEntity<ResponseDto<ToDoResponseDto>> completeTodo(@PathVariable("id") Long id,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return todoService.completeTodo(id, userDetails.getUser());
	}

	// 할일 카드 삭제 기능 (당사자만 가능, 댓글까지 같이 삭제)
	@Operation(summary = "할 일 카드 삭제", description = "당사자가 카드를 삭제할 수 있습니다.")
	@ApiResponse(responseCode = "200", description = "카드를 삭제하였습니다.",
		content = @Content(schema = @Schema(implementation = ResponseDto.class)))
	@DeleteMapping("/todo/{id}")
	public ResponseEntity<ResponseDto<ToDoResponseDto>> deleteTodo(@PathVariable("id") Long id,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return todoService.deleteTodo(id, userDetails.getUser());
	}
}
