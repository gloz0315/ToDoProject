package com.miniproject.todoproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miniproject.todoproject.entity.Todo;
import com.miniproject.todoproject.entity.User;

public interface TodoRepository extends JpaRepository<Todo, Long> {
	List<Todo> findByUser(User user);

	List<Todo> findByUserOrderByCreateAtDesc(User user);
}
