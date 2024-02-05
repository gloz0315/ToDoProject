package com.miniproject.todoproject.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.todoproject.dto.ResponseDto;
import com.miniproject.todoproject.dto.commentdto.UserCommentDto;
import com.miniproject.todoproject.dto.tododto.ToDoReadResponseDto;
import com.miniproject.todoproject.dto.tododto.ToDoRequestDto;
import com.miniproject.todoproject.dto.tododto.ToDoResponseDto;
import com.miniproject.todoproject.dto.tododto.TodoCommentResponseDto;
import com.miniproject.todoproject.dto.usersdto.UsersToDoResponseDto;
import com.miniproject.todoproject.entity.Comment;
import com.miniproject.todoproject.entity.Todo;
import com.miniproject.todoproject.entity.User;
import com.miniproject.todoproject.invalidate.Verifier;
import com.miniproject.todoproject.message.Message;
import com.miniproject.todoproject.repository.TodoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {
	private final TodoRepository todoRepository;
	private final Verifier verifier;

	public ResponseEntity<ResponseDto<List<UsersToDoResponseDto>>> getToDoList() {
		List<UsersToDoResponseDto> usersToDoResponseDtoList = new ArrayList<>();
		List<User> userList = verifier.findAllUsers();

		for (User user : userList) {
			List<Todo> todoList = todoRepository.findByUserOrderByCompleteAscCreateAtDesc(user);
			UsersToDoResponseDto list = new UsersToDoResponseDto(user.getUsername(),
				todoList.stream().map(ToDoResponseDto::new).toList());
			usersToDoResponseDtoList.add(list);
		}
		return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK, Message.READ_CARDS, usersToDoResponseDtoList));
	}

	public ResponseEntity<ResponseDto<ToDoResponseDto>> createTodo(User userInfo, ToDoRequestDto request) {
		User user = verifier.findUser(userInfo.getUsername());
		Todo todo = new Todo(request.getTitle(), request.getContents(), user);
		Todo savedTodo = todoRepository.save(todo);

		return new ResponseEntity<>(
			new ResponseDto<>(HttpStatus.CREATED, Message.CREATE_CARD, new ToDoResponseDto(savedTodo))
			, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseDto<TodoCommentResponseDto>> readToDo(Long id) {
		Todo todo = verifier.findTodo(id);
		List<Comment> findCommentList = verifier.findAllComments(todo);

		List<UserCommentDto> responseDtoList = findCommentList.stream()
			.map(comment -> new UserCommentDto(comment.getContents(), comment.getUser().getUsername()))
			.toList();

		ToDoReadResponseDto responseDto = ToDoReadResponseDto.builder()
			.title(todo.getTitle())
			.content(todo.getTitle())
			.createAt(todo.getCreateAt())
			.username(todo.getUser().getUsername())
			.build();

		return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK, Message.READ_CARD,
			new TodoCommentResponseDto(responseDto, responseDtoList)));
	}

	@Transactional
	public ResponseEntity<ResponseDto<ToDoReadResponseDto>> updateTodo(Long id, User userInfo, ToDoRequestDto request) {
		Todo todo = verifier.findTodo(id);
		User user = verifier.findUser(userInfo.getUsername());
		verifier.checkCompareUser(todo.getUser(), user);

		todo.update(request);

		return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK, Message.UPDATE_CARD, ToDoReadResponseDto.builder()
			.title(todo.getTitle())
			.content(todo.getContents())
			.createAt(todo.getCreateAt())
			.username(todo.getUser().getUsername())
			.build()));
	}

	@Transactional
	public ResponseEntity<ResponseDto<ToDoResponseDto>> completeTodo(Long id, User userInfo) {
		Todo todo = verifier.findTodo(id);
		User user = verifier.findUser(userInfo.getUsername());
		verifier.checkCompareUser(todo.getUser(), user);

		todo.updateComplete(true);

		return ResponseEntity.ok(
			new ResponseDto<>(HttpStatus.OK, Message.COMPLETE_CARD, new ToDoResponseDto(todo)));
	}

	@Transactional
	public ResponseEntity<ResponseDto<ToDoResponseDto>> deleteTodo(Long id, User user) {
		Todo todo = verifier.findTodo(id);
		verifier.checkCompareUser(todo.getUser(), user);
		todoRepository.delete(todo);

		return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK, Message.DELETE_CARD, null));
	}
}
