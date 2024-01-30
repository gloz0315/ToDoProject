package com.miniproject.todoproject.dto.tododto;

import java.util.List;

import com.miniproject.todoproject.dto.commentdto.UserCommentDto;

import lombok.Getter;

@Getter
public class TodoCommentResponseDto {
	private ToDoReadResponseDto responseDto;
	private List<UserCommentDto> commentList;

	public TodoCommentResponseDto(ToDoReadResponseDto responseDto, List<UserCommentDto> commentList) {
		this.responseDto = responseDto;
		this.commentList = commentList;
	}
}
