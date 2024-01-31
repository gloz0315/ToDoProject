package com.miniproject.todoproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miniproject.todoproject.entity.Todo;
import com.miniproject.todoproject.entity.User;

public interface TodoRepository extends JpaRepository<Todo, Long> {
	// 목록 조회시 카드 완료면 아래로 내려가고, 최신 내용들은 위에서부터 내림차순 정렬
	List<Todo> findByUserOrderByCompleteAscCreateAtDesc(User user);
}
