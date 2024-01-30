package com.miniproject.todoproject.dto.commentdto;

import com.miniproject.todoproject.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserCommentDto {
	private String contents;
	private User user;
}
