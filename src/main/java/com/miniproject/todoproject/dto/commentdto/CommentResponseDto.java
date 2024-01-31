package com.miniproject.todoproject.dto.commentdto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponseDto {
	@Schema(description = "반환되는 댓글 내용", example = "테스트 댓글입니다.")
	private String contents;
}