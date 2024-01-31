package com.miniproject.todoproject.dto.logindto;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDto {
	@Schema(description = "로그인 응답 반환 상태", example = "OK")
	private HttpStatus status;
	@Schema(description = "로그인 응답 반환 메세지", example = "로그인에 성공하셨습니다.")
	private String message;
}
