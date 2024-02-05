package com.miniproject.todoproject.invalidate;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.miniproject.todoproject.entity.Comment;
import com.miniproject.todoproject.entity.Todo;
import com.miniproject.todoproject.entity.User;
import com.miniproject.todoproject.message.Message;
import com.miniproject.todoproject.repository.CommentRepository;
import com.miniproject.todoproject.repository.TodoRepository;
import com.miniproject.todoproject.repository.UserRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class Verifier {
	private final UserRepository userRepository;
	private final TodoRepository todoRepository;
	private final CommentRepository commentRepository;
	private PasswordEncoder passwordEncoder;

	// 유저에 대한 검증 메서드들
	public User verifyUser(String username) {
		return userRepository.findByUsername(username).orElseThrow(
			() -> new IllegalArgumentException(Message.NOT_EXIST_USER)
		);
	}

	public void comparePassword(String repositoryPassword, String password) {
		if (!passwordEncoder.matches(password, repositoryPassword)) {
			throw new IllegalArgumentException(Message.NOT_MATCH_PASSWORD);
		}
	}

	public void invalidateUsername(String username) {
		if (!Invalidate.userLengthValidate(username)) {
			throw new IllegalArgumentException(Message.INVALIDATE_USERNAME);
		}
	}

	public void invalidatePassword(String password) {
		if (!Invalidate.passwordLengthValidate(password)) {
			throw new IllegalArgumentException(Message.INVALIDATE_PASSWORD);
		}
	}

	public void duplicateSName(String username) {
		if (Invalidate.duplicateUserName(userRepository.findByUsername(username))) {
			throw new IllegalArgumentException(Message.DUPLICATE_USERNAME);
		}
	}

	// Todo에 대한 검증 메서드
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	public List<Comment> findAllComments(Todo todo) {
		return commentRepository.findByTodo(todo);
	}

	public void checkCompareUser(User todoUser, User user) {
		if (!user.getUsername().equals(todoUser.getUsername())) {
			throw new IllegalArgumentException(Message.NOT_WRITER);
		}
	}

	public Todo findTodo(Long id) {
		return todoRepository.findById(id).orElseThrow(
			() -> new IllegalArgumentException(Message.NOT_EXIST_CARD)
		);
	}

	public User findUser(String username) {
		return userRepository.findByUsername(username).orElseThrow(
			() -> new IllegalArgumentException(Message.NOT_EXIST_USER)
		);
	}

	// Comment에 대한 검증
	public void existCard(Long id) {
		todoRepository.findById(id).orElseThrow(
			() -> new IllegalArgumentException(Message.NOT_EXIST_CARD)
		);
	}

	public Comment findComment(Long id) {
		return commentRepository.findById(id).orElseThrow(
			() -> new IllegalArgumentException(Message.NOT_EXIST_COMMENT)
		);
	}

	public void compareCommentUser(Long userId, Long commentUserId) {
		if (!userId.equals(commentUserId)) {
			throw new IllegalArgumentException(Message.NOT_WRITER);
		}
	}
}
