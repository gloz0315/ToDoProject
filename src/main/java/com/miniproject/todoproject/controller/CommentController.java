package com.miniproject.todoproject.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject.todoproject.dto.ResponseDto;
import com.miniproject.todoproject.dto.commentdto.CommentRequestDto;
import com.miniproject.todoproject.dto.commentdto.CommentResponseDto;
import com.miniproject.todoproject.security.UserDetailsImpl;
import com.miniproject.todoproject.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/todo")
@RequiredArgsConstructor
public class CommentController {
	private final CommentService commentService;

	// 댓글 생성 기능
	@Operation(summary = "댓글 생성", description = "댓글을 작성할 수 있습니다.")
	@PostMapping("/{id}")
	public ResponseEntity<ResponseDto<CommentResponseDto>> createComment(@PathVariable("id") Long id,
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody CommentRequestDto requestDto) {
		return commentService.createComment(id, userDetails.getUser(), requestDto);
	}

	// 댓글 기능 수정
	@Operation(summary = "댓글 수정", description = "당사자가 댓글을 수정할 수 있습니다.")
	@PutMapping("/{id}/{commentId}")
	public ResponseEntity<ResponseDto<CommentResponseDto>> updateComment(@PathVariable("id") Long id,
		@PathVariable("commentId") Long commentId,
		@AuthenticationPrincipal UserDetailsImpl userDetails,
		@RequestBody CommentRequestDto requestDto) {
		return commentService.updateComment(id, commentId, userDetails.getUser(), requestDto);
	}

	// 댓글 삭제 기능
	@Operation(summary = "댓글 삭제", description = "당사자가 댓글을 삭제할 수 있습니다.")
	@DeleteMapping("/{id}/{commentId}")
	public ResponseEntity<ResponseDto<CommentResponseDto>> deleteComment(@PathVariable("id") Long id,
		@PathVariable("commentId") Long commentId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return commentService.deleteComment(id, commentId, userDetails.getUser());
	}
}
