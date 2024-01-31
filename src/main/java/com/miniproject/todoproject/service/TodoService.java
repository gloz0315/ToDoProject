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
import com.miniproject.todoproject.message.Message;
import com.miniproject.todoproject.repository.CommentRepository;
import com.miniproject.todoproject.repository.TodoRepository;
import com.miniproject.todoproject.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {
	private final TodoRepository todoRepository;
	private final UserRepository userRepository;
	private final CommentRepository commentRepository;

	public ResponseEntity<ResponseDto<List<UsersToDoResponseDto>>> getToDoList() {
		List<UsersToDoResponseDto> usersToDoResponseDtoList = new ArrayList<>();
		List<User> userList = userRepository.findAll();

		for (User user : userList) {
			List<Todo> todoList = todoRepository.findByUserOrderByCompleteAscCreateAtDesc(user);
			UsersToDoResponseDto list = new UsersToDoResponseDto(user.getUsername(),
				todoList.stream().map(ToDoResponseDto::new).toList());
			usersToDoResponseDtoList.add(list);
		}

		return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK, Message.READ_CARDS, usersToDoResponseDtoList),
			HttpStatus.OK);
	}

	public ResponseEntity<ResponseDto<ToDoResponseDto>> createTodo(User userInfo, ToDoRequestDto request) {
		try {
			User user = userRepository.findByUsername(userInfo.getUsername()).orElseThrow(
				() -> new IllegalArgumentException(Message.NOT_EXIST_USER)
			);

			Todo todo = new Todo(request.getTitle(), request.getContents(), user);
			Todo savedTodo = todoRepository.save(todo);

			return new ResponseEntity<>(
				new ResponseDto<>(HttpStatus.CREATED, Message.CREATE_CARD, new ToDoResponseDto(savedTodo))
				, HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());

			return new ResponseEntity<>(new ResponseDto<>(HttpStatus.BAD_REQUEST, e.getMessage(), null)
				, HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<ResponseDto<TodoCommentResponseDto>> readToDo(Long id) {
		try {
			Todo todo = todoRepository.findById(id).orElseThrow(
				() -> new IllegalArgumentException(Message.NOT_EXIST_CARD)
			);

			List<Comment> findCommentList = commentRepository.findByTodo(todo);

			List<UserCommentDto> responseDtoList = findCommentList.stream()
				.map(comment -> new UserCommentDto(comment.getContents(), comment.getUser().getUsername()))
				.toList();

			ToDoReadResponseDto responseDto = ToDoReadResponseDto.builder()
				.title(todo.getTitle())
				.content(todo.getTitle())
				.createAt(todo.getCreateAt())
				.username(todo.getUser().getUsername())
				.build();

			return new ResponseEntity<>(new ResponseDto<>(HttpStatus.OK, Message.READ_CARD,
				new TodoCommentResponseDto(responseDto, responseDtoList)), HttpStatus.OK);

		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());

			return new ResponseEntity<>(
				new ResponseDto<>(HttpStatus.BAD_REQUEST, Message.NOT_EXIST_CARD, null),
				HttpStatus.BAD_REQUEST);
		}
	}

	@Transactional
	public ResponseEntity<ResponseDto<ToDoReadResponseDto>> updateTodo(Long id, User userInfo, ToDoRequestDto request) {
		try {
			Todo todo = todoRepository.findById(id).orElseThrow(
				() -> new IllegalArgumentException(Message.NOT_EXIST_CARD)
			);

			User user = userRepository.findByUsername(userInfo.getUsername()).orElseThrow(
				() -> new IllegalArgumentException(Message.NOT_EXIST_USER)
			);

			if (!user.equals(todo.getUser())) {
				throw new IllegalArgumentException(Message.NOT_WRITER);
			}

			todo.update(request);
			return new ResponseEntity<>(
				new ResponseDto<>(HttpStatus.OK, Message.UPDATE_CARD, ToDoReadResponseDto.builder()
					.title(todo.getTitle())
					.content(todo.getContents())
					.createAt(todo.getCreateAt())
					.username(todo.getUser().getUsername())
					.build()), HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(new ResponseDto<>(HttpStatus.BAD_REQUEST, e.getMessage(), null)
				, HttpStatus.BAD_REQUEST);
		}
	}

	@Transactional
	public ResponseEntity<ResponseDto<ToDoResponseDto>> completeTodo(Long id, User userInfo) {
		try {
			Todo todo = todoRepository.findById(id).orElseThrow(
				() -> new IllegalArgumentException(Message.NOT_EXIST_CARD)
			);

			User user = userRepository.findByUsername(userInfo.getUsername()).orElseThrow(
				() -> new IllegalArgumentException(Message.NOT_EXIST_USER)
			);

			if (!user.equals(todo.getUser())) {
				throw new IllegalArgumentException(Message.NOT_WRITER);
			}

			todo.updateComplete(true);

			return new ResponseEntity<>(
				new ResponseDto<>(HttpStatus.OK, Message.COMPLETE_CARD, new ToDoResponseDto(todo))
				, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(new ResponseDto<>(HttpStatus.BAD_REQUEST, e.getMessage(), null)
				, HttpStatus.BAD_REQUEST);
		}
	}
}
