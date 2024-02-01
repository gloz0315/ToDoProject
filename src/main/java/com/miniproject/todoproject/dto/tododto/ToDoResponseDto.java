package com.miniproject.todoproject.dto.tododto;

import java.time.LocalDateTime;

import com.miniproject.todoproject.entity.Todo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ToDoResponseDto {
	@Schema(description = "할 일 카드 제목", example = "테스트 제목")
	private String title;
	@Schema(description = "할 일 카드 내용", example = "테스트 내용")
	private String contents;
	@Schema(description = "생성일")
	private LocalDateTime createAt;
	@Schema(description = "할 일 카드 완료 여부", example = "false")
	private boolean complete;

	public ToDoResponseDto(Todo todo) {
		this.title = todo.getTitle();
		this.contents = todo.getContents();
		this.createAt = todo.getCreateAt();
		this.complete = todo.isComplete();
	}
}
