package com.miniproject.todoproject.service;

import org.springframework.stereotype.Service;

import com.miniproject.todoproject.dto.commentdto.CommentRequestDto;
import com.miniproject.todoproject.dto.commentdto.CommentResponseDto;
import com.miniproject.todoproject.entity.Comment;
import com.miniproject.todoproject.entity.Todo;
import com.miniproject.todoproject.entity.User;
import com.miniproject.todoproject.repository.CommentRepository;
import com.miniproject.todoproject.repository.TodoRepository;
import com.miniproject.todoproject.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final TodoRepository todoRepository;
	private final UserRepository userRepository;
	private final CommentRepository commentRepository;

	public CommentResponseDto createComment(Long id, User userInfo, CommentRequestDto requestDto) {
		Todo todo = todoRepository.findById(id).orElseThrow(
			() -> new IllegalArgumentException("해당 카드가 존재하지 않습니다.")
		);

		User user = userRepository.findByUsername(userInfo.getUsername()).orElseThrow(
			() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.")
		);

		Comment comment = new Comment(requestDto.getContents(), user, todo);
		commentRepository.save(comment);

		return new CommentResponseDto(comment.getContents());
	}
}
