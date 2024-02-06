package com.miniproject.todoproject.invalidate;

import java.util.List;

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

	// Todo에 대한 검증 메서드
	public List<User> findAllUsers() {
		return userRepository.findAll();
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
		return userRepository.findByUserNameOrElseThrow(username);
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
