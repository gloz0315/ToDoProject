package com.miniproject.todoproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miniproject.todoproject.entity.Comment;
import com.miniproject.todoproject.entity.Todo;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findByTodo(Todo todo);
}
