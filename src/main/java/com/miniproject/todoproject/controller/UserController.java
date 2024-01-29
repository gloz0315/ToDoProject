package com.miniproject.todoproject.controller;

import com.miniproject.todoproject.dto.ResponseDto;
import com.miniproject.todoproject.dto.SignupRequestDto;
import com.miniproject.todoproject.dto.UserInfoDto;
import com.miniproject.todoproject.entity.UserRoleEnum;
import com.miniproject.todoproject.security.UserDetailsImpl;
import com.miniproject.todoproject.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    
    @GetMapping("/user/login-page")
    public String loginPage() {
        return "login";
    }
    
    @GetMapping("/user/signup")
    public String signupPage() {
        return "signup";
    }

    // 회원 가입하기
    @PostMapping("/user/signup")
    public String signup(@Valid SignupRequestDto requestDto, BindingResult bindingResult) {
        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(!fieldErrors.isEmpty()) {
            for(FieldError fieldError : fieldErrors) {
                log.error(fieldError.getField() + "필드 : " + fieldError.getDefaultMessage());
            }
            return "redirect:/api/user/signup";
        }
        
        userService.signup(requestDto);
        
        return "redirect:/api/user/login-page";
    }

    @GetMapping("/user-info")
    @ResponseBody
    public UserInfoDto getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username= userDetails.getUsername();
        UserRoleEnum role = userDetails.getUser().getRole();
        boolean isAdmin = (role == UserRoleEnum.ADMIN);

        return new UserInfoDto(username, isAdmin);
    }

}
