package com.miniproject.todoproject.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.miniproject.todoproject.dto.logindto.LoginRequestDto;
import com.miniproject.todoproject.dto.logindto.LoginResponseDto;
import com.miniproject.todoproject.dto.signupdto.SignupRequestDto;
import com.miniproject.todoproject.entity.User;
import com.miniproject.todoproject.invalidate.Invalidate;
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

	public ResponseEntity<LoginResponseDto> login(HttpServletResponse response, LoginRequestDto request) {
		String username = request.getUsername();
		String password = request.getPassword();

		Optional<User> user = userRepository.findByUsername(username);

		existUsername(user);
		comparePassword(user.get().getPassword(), password);

		response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.get().getUsername()));

		return new ResponseEntity<>(new LoginResponseDto(HttpStatus.OK, Message.SUCCESS_LOGIN), HttpStatus.OK);
	}

	public ResponseEntity<LoginResponseDto> signup(SignupRequestDto requestDto) {
		String username = requestDto.getUsername();
		String password = requestDto.getPassword();

		invalidateUsername(username);
		invalidatePassword(password);
		duplicateSName(username);

		User user = new User(username, passwordEncoder.encode(password));
		userRepository.save(user);

		return ResponseEntity.ok(new LoginResponseDto(HttpStatus.OK, Message.SUCCESS_SIGNUP));
	}

	private void existUsername(Optional<User> user) {
		if (user.isEmpty()) {
			throw new IllegalArgumentException(Message.NOT_EXIST_USER);
		}
	}

	private void comparePassword(String repositoryPassword, String password) {
		if (!passwordEncoder.matches(password, repositoryPassword)) {
			throw new IllegalArgumentException(Message.NOT_MATCH_PASSWORD);
		}
	}

	private void invalidateUsername(String username) {
		if (!Invalidate.userLengthValidate(username)) {
			throw new IllegalArgumentException(Message.INVALIDATE_USERNAME);
		}
	}

	private void invalidatePassword(String password) {
		if (!Invalidate.passwordLengthValidate(password)) {
			throw new IllegalArgumentException(Message.INVALIDATE_PASSWORD);
		}
	}

	private void duplicateSName(String username) {
		if (Invalidate.duplicateUserName(userRepository.findByUsername(username))) {
			throw new IllegalArgumentException(Message.DUPLICATE_USERNAME);
		}
	}

}
