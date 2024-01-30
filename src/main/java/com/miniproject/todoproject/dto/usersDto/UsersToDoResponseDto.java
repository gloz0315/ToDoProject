package com.miniproject.todoproject.dto.usersdto;

import java.util.ArrayList;
import java.util.List;

import com.miniproject.todoproject.dto.tododto.ToDoResponseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UsersToDoResponseDto {
	private String username;
	private List<ToDoResponseDto> todoList = new ArrayList<>();

	public UsersToDoResponseDto(String username, List<ToDoResponseDto> responseList) {
		this.username = username;
		this.todoList = responseList;
	}
}
