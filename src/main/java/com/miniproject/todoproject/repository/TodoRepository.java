package com.miniproject.todoproject.repository;

import com.miniproject.todoproject.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
