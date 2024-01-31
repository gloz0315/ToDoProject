package com.miniproject.todoproject.dto.commentdto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserCommentDto {
	private String contents;
	private String username;
}
