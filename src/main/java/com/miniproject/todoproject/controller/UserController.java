package com.miniproject.todoproject.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.miniproject.todoproject.dto.logindto.LoginRequestDto;
import com.miniproject.todoproject.dto.logindto.LoginResponseDto;
import com.miniproject.todoproject.dto.signupdto.SignupRequestDto;
import com.miniproject.todoproject.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "User 컨트롤러", description = "회원 가입 및 로그인 하는 API 입니다.")
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
	private final UserService userService;

	// 회원 가입하기
	@Operation(summary = "회원 가입", description = "회원의 이름과 비밀번호를 통해 가입할 수 있습니다.")
	@ApiResponse(responseCode = "200", description = "회원가입에 성공하셨습니다.",
		content = @Content(schema = @Schema(implementation = LoginResponseDto.class)))
	@PostMapping("/signup")
	@ResponseBody
	public ResponseEntity<LoginResponseDto> signup(@RequestBody SignupRequestDto request) {
		return userService.signup(request);
	}

	// 로그인 하기
	@Operation(summary = "로그인", description = "회원의 이름과 비밀번호를 통해 로그인할 수 있습니다.")
	@ApiResponse(responseCode = "200", description = "로그인에 성공하셨습니다.",
		content = @Content(schema = @Schema(implementation = LoginResponseDto.class)))
	@PostMapping("/login")
	@ResponseBody
	public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto request, HttpServletResponse response) {
		return userService.login(response, request);
	}

}
