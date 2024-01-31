package com.miniproject.todoproject.dto.tododto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ToDoReadResponseDto {
	@Schema(description = "할 일 카드 제목", example = "테스트 제목")
	private String title;
	@Schema(description = "할 일 카드 내용", example = "테스트 내용")
	private String content;
	@Schema(description = "작성한 유저 이름", example = "robbie")
	private String username;
	@Schema(description = "생성일")
	private LocalDateTime createAt;

}
