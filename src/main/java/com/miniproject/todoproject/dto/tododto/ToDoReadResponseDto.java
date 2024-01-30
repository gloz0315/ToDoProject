package com.miniproject.todoproject.dto.tododto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ToDoReadResponseDto {
	private String title;
	private String content;
	private String username;
	private LocalDateTime createAt;

}
