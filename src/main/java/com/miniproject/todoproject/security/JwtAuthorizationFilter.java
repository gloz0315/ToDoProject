package com.miniproject.todoproject.security;

import java.io.IOException;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miniproject.todoproject.dto.ResponseDto;
import com.miniproject.todoproject.jwtUtil.JwtUtil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "JWT 검증")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
	private final JwtUtil jwtUtil;
	private final UserDetailsServiceImpl userDetailsService;
	private final ObjectMapper objectMapper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String token = jwtUtil.resolveToken(request);

		if (Objects.nonNull(token)) {
			if (jwtUtil.validateToken(token)) {
				Claims info = jwtUtil.getUserInfoFromToken(token);

				// 인증정보에 요저정보(username) 넣기
				// username -> user 조회
				String username = info.getSubject();
				SecurityContext context = SecurityContextHolder.createEmptyContext();
				// -> userDetails 에 담고
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				// -> authentication의 principal 에 담고
				Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
					userDetails.getAuthorities());
				// -> securityContent 에 담고
				context.setAuthentication(authentication);
				// -> SecurityContextHolder 에 담고
				SecurityContextHolder.setContext(context);
				// -> 이제 @AuthenticationPrincipal 로 조회할 수 있음
			} else {
				// 인증정보가 존재하지 않을때
				ResponseDto commonResponseDto = new ResponseDto(HttpStatus.BAD_REQUEST, "토큰이 유효하지 않습니다.", null);
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.setContentType("application/json; charset=UTF-8");
				response.getWriter().write(objectMapper.writeValueAsString(commonResponseDto));
				return;
			}
		}

		filterChain.doFilter(request, response);
	}

	// 인증 처리
	public void setAuthentication(String username) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		Authentication authentication = createAuthentication(username);
		context.setAuthentication(authentication);

		SecurityContextHolder.setContext(context);
	}

	// 인증 객체 생성
	private Authentication createAuthentication(String username) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}
}
