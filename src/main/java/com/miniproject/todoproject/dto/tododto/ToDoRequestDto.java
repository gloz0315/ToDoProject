package com.miniproject.todoproject.dto.tododto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ToDoRequestDto {
	@NotNull(message = "title cannot be null")
	@Schema(description = "할 일 카드 제목", example = "테스트 제목")
	private String title;
	@NotNull(message = "contents cannot be null")
	@Schema(description = "할 일 카드 내용", example = "테스트 내용")
	private String contents;
}
