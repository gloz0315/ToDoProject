package com.miniproject.todoproject.controller;

import com.miniproject.todoproject.dto.logindto.LoginRequestDto;
import com.miniproject.todoproject.dto.logindto.LoginResponseDto;
import com.miniproject.todoproject.dto.signupdto.SignupRequestDto;
import com.miniproject.todoproject.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    // 회원 가입하기
    @PostMapping("/user/signup")
    @ResponseBody
    public LoginResponseDto signup(@RequestBody SignupRequestDto request) {
        return userService.signup(request);
    }

    // 로그인 하기
    @GetMapping("/user/login")
    @ResponseBody
    public LoginResponseDto login(@RequestBody LoginRequestDto request, HttpServletResponse response) {
        return userService.login(response,request);
    }
    

}
