package com.miniproject.todoproject.dto.commentdto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
	@NotNull(message = "contents cannot be null")
	@Schema(description = "댓글 내용", example = "테스트 댓글 입니다.")
	private String contents;
}
