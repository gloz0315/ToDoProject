package com.miniproject.todoproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miniproject.todoproject.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
