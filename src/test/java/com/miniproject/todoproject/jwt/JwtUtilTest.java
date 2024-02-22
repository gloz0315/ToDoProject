package com.miniproject.todoproject.jwt;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.miniproject.todoproject.jwtUtil.JwtUtil;
import com.miniproject.todoproject.test.CommonTest;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

@SpringBootTest
@ActiveProfiles("test")
public class JwtUtilTest implements CommonTest {

	@Autowired
	JwtUtil jwtUtil;

	@Mock
	private HttpServletRequest request;

	// 각각의 테스트 코드를 시작하기 전에 얘를 실행한다.
	@BeforeEach
	void setup() {
		jwtUtil.init();
	}

	@DisplayName("토큰 생성")
	@Test
	void createToken() {
		// when
		String token = jwtUtil.createToken(TEST_USER_NAME);

		// then
		assertNotNull(token);
	}

	@DisplayName("토큰 추출")
	@Test
	void resolveToken() {
		// given
		String token = "Bearer test-token";

		// when
		given(request.getHeader(JwtUtil.AUTHORIZATION_HEADER)).willReturn(token);
		String resolvedToken = jwtUtil.resolveToken(request);

		// then
		assertEquals("test-token", resolvedToken);
	}

	@DisplayName("토큰 검증")
	@Nested
	class validateToken {

		@DisplayName("토큰 검증 성공")
		@Test
		void validateToken_success() {
			// given
			String token = jwtUtil.createToken(TEST_USER_NAME).substring(7);

			// when
			boolean isValid = jwtUtil.validateToken(token);

			// then
			assertTrue(isValid);
		}

		@DisplayName("토큰 검증 실패")
		@Test
		void validateToken_fail() {
			// given
			String invalidToken = "invalid-token";

			// when
			boolean isValid = jwtUtil.validateToken(invalidToken);

			// then
			assertFalse(isValid);
		}
	}

	@DisplayName("토큰에서 UserInfo 조회")
	@Test
	void getUserInfoFromToken() {
		// given
		String token = jwtUtil.createToken(TEST_USER_NAME).substring(7);

		// when
		Claims claims = jwtUtil.getUserInfoFromToken(token);

		// then
		assertNotNull(claims);
		assertEquals(TEST_USER_NAME, claims.getSubject());
	}
}
