package com.miniproject.todoproject.dto;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T> {
	@Schema(description = "메세지 반환 상태", example = "HTTP.OK")
	private HttpStatus status;
	@Schema(description = "메세지 반환 내용", example = "성공하였습니다.")
	private String message;
	@Schema(description = "반환될 데이터", example = "반환될 객체 타입에 따라 달라짐")
	private T data;
}
