package com.miniproject.todoproject.service;

import com.miniproject.todoproject.dto.tododto.ToDoRequest;
import com.miniproject.todoproject.dto.tododto.ToDoResponse;
import com.miniproject.todoproject.dto.UsersToDoResponseDto;
import com.miniproject.todoproject.entity.Todo;
import com.miniproject.todoproject.entity.User;
import com.miniproject.todoproject.repository.TodoRepository;
import com.miniproject.todoproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    public List<UsersToDoResponseDto> getToDoList() {
        List<UsersToDoResponseDto> usersToDoResponseDtoList = new ArrayList<>();
        List<User> userList = userRepository.findAll();

        for(User user : userList) {
            List<Todo> todoList = todoRepository.findByUser(user);
            UsersToDoResponseDto list = new UsersToDoResponseDto(user.getUsername(),
                    todoList.stream().map(ToDoResponse::new).toList());
            usersToDoResponseDtoList.add(list);
        }

        return usersToDoResponseDtoList;
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
