package com.miniproject.todoproject.user;

import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.miniproject.todoproject.dto.signupdto.SignupRequestDto;
import com.miniproject.todoproject.test.CommonTest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class UserRequestDTOTest implements CommonTest {

	@Nested
	@DisplayName("유저 요청 DTO 생성")
	class createSignupRequestDto {

		@DisplayName("유저 요청 DTO 생성 성공")
		@Test
		void createUserSignupRequestDTO_success() {
			// given
			SignupRequestDto dto = new SignupRequestDto();
			dto.setUsername(TEST_USER_NAME);
			dto.setPassword(TEST_USER_PASSWORD);

			// when
			Set<ConstraintViolation<SignupRequestDto>> violations = validate(dto);

			// then
			Assertions.assertThat(violations).isEmpty();
		}

		@DisplayName("유저 요청 DTO 생성 실패 - 잘못된 username")
		@Test
		void createUserSignupRequestDTO_fail_wrongUserName() {
			// given
			SignupRequestDto dto = new SignupRequestDto();
			dto.setUsername("invalid_wrong_username_length");
			dto.setPassword(TEST_USER_PASSWORD);

			// when
			Set<ConstraintViolation<SignupRequestDto>> violations = validate(dto);

			// then
			Assertions.assertThat(violations).hasSize(1);
			Assertions.assertThat(violations)
				.extracting("message")
				.contains("\"^[a-z0-9]{4,10}$\"와 일치해야 합니다");
		}

		@DisplayName("유저 요청 DTO 생성 실패 - 잘못된 password")
		@Test
		void createUserSignupRequestDTO_fail_wrongPassword() {
			SignupRequestDto dto = new SignupRequestDto();
			dto.setUsername(TEST_USER_NAME);
			dto.setPassword("Invalid password");

			// when
			Set<ConstraintViolation<SignupRequestDto>> violations = validate(dto);

			// then
			Assertions.assertThat(violations).hasSize(1);
			Assertions.assertThat(violations)
				.extracting("message")
				.contains("\"^[a-z0-9]{8,15}$\"와 일치해야 합니다");
		}
	}

	// 이 객체의 값이 @Pattern 에 만족하는지 체크를 해주는 메서드임
	// 검증 함수를 실행하여서 검증이 만족하는지, 결과값을 Set에 넣어줌 -> 예외가 발생한다면 값이 들어감
	private Set<ConstraintViolation<SignupRequestDto>> validate(SignupRequestDto dto) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		return validator.validate(dto);
	}
}
