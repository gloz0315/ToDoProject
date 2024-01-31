package com.miniproject.todoproject.dto.signupdto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
	@NotNull(message = "최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)")
	@Schema(description = "회원 가입할 유저의 이름", example = "robbie")
	private String username;
	@NotNull(message = "최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9)")
	@Schema(description = "회원 가입할 유저의 비밀번호", example = "Skt123456789")
	private String password;
}
