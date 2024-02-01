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

		if (!existUsername(user)) {
			return new ResponseEntity<>(new LoginResponseDto(HttpStatus.NOT_FOUND, Message.NOT_EXIST_USERNAME)
				, HttpStatus.BAD_REQUEST);
		}

		if (!comparePassword(user.get().getPassword(), password)) {
			return new ResponseEntity<>(new LoginResponseDto(HttpStatus.BAD_REQUEST, Message.NOT_MATCH_PASSWORD)
				, HttpStatus.BAD_REQUEST);
		}

		// 헤더에 Jwt 토큰을 통한 정보 반환
		response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.get().getUsername()));

		return new ResponseEntity<>(new LoginResponseDto(HttpStatus.OK, Message.SUCCESS_LOGIN), HttpStatus.OK);
	}

	public ResponseEntity<LoginResponseDto> signup(SignupRequestDto requestDto) {
		String username = requestDto.getUsername();
		String password = requestDto.getPassword();

		if (!invalidateUsername(username)) {
			return new ResponseEntity<>(new LoginResponseDto(HttpStatus.BAD_REQUEST, Message.ERROR_USERNAME),
				HttpStatus.BAD_REQUEST);
		}

		if (!invalidatePassword(password)) {
			return new ResponseEntity<>(new LoginResponseDto(HttpStatus.BAD_REQUEST, Message.ERROR_PASSWORD),
				HttpStatus.BAD_REQUEST);
		}

		if (duplicateSName(username)) {
			return new ResponseEntity<>(new LoginResponseDto(HttpStatus.BAD_REQUEST, Message.DUPLICATE_USERNAME),
				HttpStatus.BAD_REQUEST);
		}

		User user = new User(username, passwordEncoder.encode(password));
		userRepository.save(user);

		return new ResponseEntity<>(new LoginResponseDto(HttpStatus.OK, Message.SUCCESS_SIGNUP),
			HttpStatus.OK);
	}

	private boolean existUsername(Optional<User> user) {
		try {
			if (user.isEmpty()) {
				throw new IllegalArgumentException(Message.NOT_EXIST_USER);
			}
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			return false;
		}
		return true;
	}

	private boolean comparePassword(String repositoryPassword, String password) {
		try {
			if (!passwordEncoder.matches(password, repositoryPassword)) {
				throw new IllegalArgumentException(Message.NOT_MATCH_PASSWORD);
			}
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			return false;
		}
		return true;
	}

	private boolean invalidateUsername(String username) {
		try {
			if (!Invalidate.userLengthValidate(username)) {
				throw new IllegalArgumentException(Message.INVALIDATE_USERNAME);
			}
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			return false;
		}
		return true;
	}

	private boolean invalidatePassword(String password) {
		try {
			if (!Invalidate.passwordLengthValidate(password)) {
				throw new IllegalArgumentException(Message.INVALIDATE_PASSWORD);
			}
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			return false;
		}
		return true;
	}

	private boolean duplicateSName(String username) {
		try {
			if (Invalidate.duplicateUserName(userRepository.findByUsername(username))) {
				throw new IllegalArgumentException(Message.DUPLICATE_USERNAME);
			}
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			return true;
		}
		return false;
	}

}
