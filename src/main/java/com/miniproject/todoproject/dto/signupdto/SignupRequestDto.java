package com.miniproject.todoproject.dto.signupdto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
	private String username;
	private String password;
}
