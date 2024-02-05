package com.miniproject.todoproject.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.miniproject.todoproject.dto.logindto.LoginRequestDto;
import com.miniproject.todoproject.dto.logindto.LoginResponseDto;
import com.miniproject.todoproject.dto.signupdto.SignupRequestDto;
import com.miniproject.todoproject.entity.User;
import com.miniproject.todoproject.invalidate.Verifier;
import com.miniproject.todoproject.jwtUtil.JwtUtil;
import com.miniproject.todoproject.message.Message;
import com.miniproject.todoproject.repository.UserRepository;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;
	private final PasswordEncoder passwordEncoder;
	private final Verifier verifier;

	public ResponseEntity<LoginResponseDto> login(HttpServletResponse response, LoginRequestDto request) {
		String username = request.getUsername();
		String password = request.getPassword();

		User user = verifier.verifyUser(username);

		verifier.comparePassword(user.getPassword(), password);

		response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername()));

		return new ResponseEntity<>(new LoginResponseDto(HttpStatus.OK, Message.SUCCESS_LOGIN), HttpStatus.OK);
	}

	public ResponseEntity<LoginResponseDto> signup(SignupRequestDto requestDto) {
		String username = requestDto.getUsername();
		String password = requestDto.getPassword();

		verifier.invalidateUsername(username);
		verifier.invalidatePassword(password);
		verifier.duplicateSName(username);

		User user = new User(username, passwordEncoder.encode(password));
		userRepository.save(user);

		return ResponseEntity.ok(new LoginResponseDto(HttpStatus.OK, Message.SUCCESS_SIGNUP));
	}
}
