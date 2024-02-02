package com.miniproject.todoproject.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miniproject.todoproject.dto.ResponseDto;
import com.miniproject.todoproject.dto.commentdto.CommentRequestDto;
import com.miniproject.todoproject.dto.commentdto.CommentResponseDto;
import com.miniproject.todoproject.entity.Comment;
import com.miniproject.todoproject.entity.Todo;
import com.miniproject.todoproject.entity.User;
import com.miniproject.todoproject.message.Message;
import com.miniproject.todoproject.repository.CommentRepository;
import com.miniproject.todoproject.repository.TodoRepository;
import com.miniproject.todoproject.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {
	private final TodoRepository todoRepository;
	private final UserRepository userRepository;
	private final CommentRepository commentRepository;

	public ResponseEntity<ResponseDto<CommentResponseDto>> createComment(Long id, User userInfo,
		CommentRequestDto requestDto) {
		Todo todo = findTodo(id);
		User user = findUser(userInfo.getUsername());

		Comment comment = new Comment(requestDto.getContents(), user);
		todo.addCommentList(comment);
		commentRepository.save(comment);

		return new ResponseEntity<>(
			new ResponseDto<>(HttpStatus.CREATED, Message.CREATE_COMMENT,
				new CommentResponseDto(requestDto.getContents()))
			, HttpStatus.CREATED);

	}

	@Transactional
	public ResponseEntity<ResponseDto<CommentResponseDto>> updateComment(Long id, Long commentId, User userInfo,
		CommentRequestDto requestDto) {
		existCard(id);
		Comment comment = findComment(commentId);
		compareCommentUser(userInfo.getId(), comment.getUser().getId());

		comment.update(requestDto.getContents());
		return ResponseEntity
			.ok(new ResponseDto<>(HttpStatus.OK, Message.UPDATE_COMMENT,
				new CommentResponseDto(comment.getContents())));
	}

	public ResponseEntity<ResponseDto<CommentResponseDto>> deleteComment(Long id, Long commentId, User userInfo) {
		existCard(id);
		Comment comment = findComment(commentId);
		compareCommentUser(userInfo.getId(), comment.getUser().getId());

		String contents = comment.getContents();
		commentRepository.delete(comment);

		return ResponseEntity
			.ok(new ResponseDto<>(HttpStatus.OK, Message.DELETE_COMMENT, new CommentResponseDto(contents)));
	}

	private void existCard(Long id) {
		todoRepository.findById(id).orElseThrow(
			() -> new IllegalArgumentException(Message.NOT_EXIST_CARD)
		);
	}

	private Todo findTodo(Long id) {
		return todoRepository.findById(id).orElseThrow(
			() -> new IllegalArgumentException(Message.NOT_EXIST_CARD)
		);
	}

	private User findUser(String username) {
		return userRepository.findByUsername(username).orElseThrow(
			() -> new IllegalArgumentException(Message.NOT_EXIST_USER)
		);
	}

	private Comment findComment(Long id) {
		return commentRepository.findById(id).orElseThrow(
			() -> new IllegalArgumentException(Message.NOT_EXIST_COMMENT)
		);
	}

	private void compareCommentUser(Long userId, Long commentUserId) {
		if (!userId.equals(commentUserId)) {
			throw new IllegalArgumentException(Message.NOT_WRITER);
		}
	}
}
