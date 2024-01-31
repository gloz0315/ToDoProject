package com.miniproject.todoproject.dto.usersdto;

import java.util.ArrayList;
import java.util.List;

import com.miniproject.todoproject.dto.tododto.ToDoResponseDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UsersToDoResponseDto {
	@Schema(description = "유저 이름", example = "robbie")
	private String username;
	@Schema(description = "전체 할일 카드 목록들")
	private List<ToDoResponseDto> todoList = new ArrayList<>();

	public UsersToDoResponseDto(String username, List<ToDoResponseDto> responseList) {
		this.username = username;
		this.todoList = responseList;
	}
}
