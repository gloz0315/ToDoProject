package com.miniproject.todoproject.repository;

import com.miniproject.todoproject.entity.Todo;
import com.miniproject.todoproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByUser(User user);
}
