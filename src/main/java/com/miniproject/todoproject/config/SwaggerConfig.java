package com.miniproject.todoproject.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@OpenAPIDefinition(
	info = @Info(title = "Todo Backend Web API 명세서",
		description = "로그인, 회원가입, 댓글 기능 있는 투두앱 백엔드 서버 만들기 API 명세서",
		version = "V1"))
@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {
		SecurityScheme securityScheme = new SecurityScheme().
			type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
			.in(SecurityScheme.In.HEADER).name("Authorization");

		SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

		return new OpenAPI().
			components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
			.security(Arrays.asList(securityRequirement));
	}
}
