package com.miniproject.todoproject.service;

import com.miniproject.todoproject.dto.tododto.ToDoReadResponseDto;
import com.miniproject.todoproject.dto.tododto.ToDoRequestDto;
import com.miniproject.todoproject.dto.tododto.ToDoResponseDto;
import com.miniproject.todoproject.dto.usersDto.UsersToDoResponseDto;
import com.miniproject.todoproject.entity.Todo;
import com.miniproject.todoproject.entity.User;
import com.miniproject.todoproject.repository.TodoRepository;
import com.miniproject.todoproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                    todoList.stream().map(ToDoResponseDto::new).toList());
            usersToDoResponseDtoList.add(list);
        }

        return usersToDoResponseDtoList;
    }

    public ToDoResponseDto createTodo(User userInfo, ToDoRequestDto request) {
        User user = userRepository.findByUsername(userInfo.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.")
        );
        Todo todo = new Todo(request.getTitle(), request.getContents(), user);
        Todo savedTodo = todoRepository.save(todo);

        return new ToDoResponseDto(savedTodo);
    }

    public ToDoReadResponseDto readToDo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 카드가 존재하지 않습니다.")
        );

        return ToDoReadResponseDto.builder()
                .title(todo.getTitle())
                .content(todo.getTitle())
                .createAt(todo.getCreateAt())
                .username(todo.getUser().getUsername())
                .build();
    }

    @Transactional
    public ToDoReadResponseDto updateTodo(Long id,User userInfo, ToDoRequestDto request) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 카드가 존재하지 않습니다.")
        );

        User user = userRepository.findByUsername(userInfo.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.")
        );

        if(!user.equals(todo.getUser())) {
            throw new IllegalArgumentException("카드를 만든 당사자가 아닙니다.");
        }

        todo.update(request);

        return ToDoReadResponseDto.builder()
                .title(todo.getTitle())
                .content(todo.getContents())
                .createAt(todo.getCreateAt())
                .username(todo.getUser().getUsername())
                .build();
    }

    @Transactional
    public ToDoResponseDto completeTodo(Long id, User userInfo) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 카드가 존재하지 않습니다.")
        );

        User user = userRepository.findByUsername(userInfo.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.")
        );

        if(!user.equals(todo.getUser())) {
            throw new IllegalArgumentException("카드를 만든 당사자가 아닙니다.");
        }

        todo.setComplete(true);

        return new ToDoResponseDto(todo);
    }
}
