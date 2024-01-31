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
		try {
			Todo todo = findTodo(id);
			User user = findUser(userInfo.getUsername());

			Comment comment = new Comment(requestDto.getContents(), user, todo);
			commentRepository.save(comment);

			return new ResponseEntity<>(
				new ResponseDto<>(HttpStatus.CREATED, Message.CREATE_COMMENT,
					new CommentResponseDto(comment.getContents()))
				, HttpStatus.CREATED);

		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(new ResponseDto<>(HttpStatus.BAD_REQUEST, e.getMessage(), null)
				, HttpStatus.BAD_REQUEST);
		}

	}

	@Transactional
	public ResponseEntity<ResponseDto<CommentResponseDto>> updateComment(Long id, Long commentId, User userInfo,
		CommentRequestDto requestDto) {
		try {
			existCard(id);
			Comment comment = findComment(commentId);
			compareCommentUser(userInfo.getId(), comment.getUser().getId());

			comment.update(requestDto.getContents());
			return new ResponseEntity<>(
				new ResponseDto<>(HttpStatus.OK, Message.UPDATE_COMMENT, new CommentResponseDto(comment.getContents()))
				, HttpStatus.OK
			);
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(new ResponseDto<>(HttpStatus.BAD_REQUEST, e.getMessage(), null)
				, HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<ResponseDto<CommentResponseDto>> deleteComment(Long id, Long commentId, User userInfo) {
		try {
			existCard(id);
			Comment comment = findComment(commentId);
			compareCommentUser(userInfo.getId(), comment.getUser().getId());

			String contents = comment.getContents();
			commentRepository.delete(comment);

			return new ResponseEntity<>(
				new ResponseDto<>(HttpStatus.OK, Message.DELETE_COMMENT, new CommentResponseDto(contents)),
				HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(new ResponseDto<>(HttpStatus.BAD_REQUEST, e.getMessage(), null)
				, HttpStatus.BAD_REQUEST);
		}
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
