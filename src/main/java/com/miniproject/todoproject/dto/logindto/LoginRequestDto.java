package com.miniproject.todoproject.dto.logindto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
	@NotNull(message = "회원 가입한 유저만 가능합니다.")
	@Schema(description = "로그인 유저 이름", example = "robbie")
	private String username;
	@NotNull(message = "회원 가입된 유저의 비밀번호를 적으시면 됩니다.")
	@Schema(description = "로그인 유저 비밀번호", example = "Skt123456789")
	private String password;
}
