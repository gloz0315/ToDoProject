package com.miniproject.todoproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miniproject.todoproject.entity.User;
import com.miniproject.todoproject.message.Message;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	default User findByUserNameOrElseThrow(String username) {
		return findByUsername(username).orElseThrow(
			() -> new IllegalArgumentException(Message.NOT_EXIST_USER)
		);
	}
}
