package com.miniproject.todoproject.controller;

import com.miniproject.todoproject.dto.LoginResponseDto;
import com.miniproject.todoproject.dto.SignupRequestDto;
import com.miniproject.todoproject.dto.UserInfoDto;
import com.miniproject.todoproject.security.UserDetailsImpl;
import com.miniproject.todoproject.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @PostMapping("/user/login")
    @ResponseBody
    public LoginResponseDto login(@RequestBody SignupRequestDto request, HttpServletResponse response) {
        return userService.login(response,request);
    }
    

}
