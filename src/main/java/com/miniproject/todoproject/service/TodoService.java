package com.miniproject.todoproject.service;

import com.miniproject.todoproject.dto.ResponseDto;
import com.miniproject.todoproject.dto.ToDoRequest;
import com.miniproject.todoproject.dto.ToDoResponse;
import com.miniproject.todoproject.entity.Todo;
import com.miniproject.todoproject.entity.User;
import com.miniproject.todoproject.repository.TodoRepository;
import com.miniproject.todoproject.repository.UserRepository;
import com.miniproject.todoproject.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    public List<ToDoResponse> getToDoList(User user) {
        List<Todo> todoList = todoRepository.findByUser(user);

        return todoList.stream().map(ToDoResponse::new).toList();
    }

    public ToDoResponse createTodo(User userInfo, ToDoRequest request) {
        User user = userRepository.findByUsername(userInfo.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.")
        );
        Todo todo = new Todo(request.getTitle(), request.getContents(), user);
        Todo savedTodo = todoRepository.save(todo);

        return new ToDoResponse(savedTodo);
    }
}
