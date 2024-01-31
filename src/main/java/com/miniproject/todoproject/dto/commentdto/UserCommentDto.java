package com.miniproject.todoproject.dto.commentdto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserCommentDto {

	@Schema(description = "작성한 댓글 내용입니다.", example = "댓글 내용입니다.")
	private String contents;

	@Schema(description = "작성한 댓글의 유저입니다.", example = "robbie")
	private String username;
}
