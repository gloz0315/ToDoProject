package com.miniproject.todoproject.dto.tododto;

import java.util.List;

import com.miniproject.todoproject.dto.commentdto.UserCommentDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class TodoCommentResponseDto {
	@Schema(description = "카드 정보 조회", example = "제목, 내용, 유저 이름, 생성일")
	private ToDoReadResponseDto responseDto;
	@Schema(description = "댓글들 정보 조회")
	private List<UserCommentDto> commentList;

	public TodoCommentResponseDto(ToDoReadResponseDto responseDto, List<UserCommentDto> commentList) {
		this.responseDto = responseDto;
		this.commentList = commentList;
	}
}
