package com.miniproject.todoproject.service;

import com.miniproject.todoproject.dto.logindto.LoginRequestDto;
import com.miniproject.todoproject.dto.logindto.LoginResponseDto;
import com.miniproject.todoproject.dto.signupdto.SignupRequestDto;
import com.miniproject.todoproject.entity.User;
import com.miniproject.todoproject.invalidate.Invalidate;
import com.miniproject.todoproject.jwtUtil.JwtUtil;
import com.miniproject.todoproject.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public LoginResponseDto login(HttpServletResponse response, LoginRequestDto request) {
        String username = request.getUsername();
        String password = request.getPassword();

        Optional<User> user = userRepository.findByUsername(username);

        if(!existUsername(user)) {
            return new LoginResponseDto(HttpStatus.BAD_REQUEST, "유저 이름이 존재하지 않습니다.");
        }

        if(!comparePassword(user.get().getPassword(),password)) {
            return new LoginResponseDto(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        // 헤더에 Jwt 토큰을 통한 정보 반환
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.get().getUsername()));

        return new LoginResponseDto(HttpStatus.OK,"로그인에 성공하셨습니다.");
    }

    public LoginResponseDto signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        if(!invalidateUsername(username)) {
            return new LoginResponseDto(HttpStatus.BAD_REQUEST,"이름을 잘못 작성하셨습니다.");
        }

        if(!invalidatePassword(password)) {
            return new LoginResponseDto(HttpStatus.BAD_REQUEST,"비밀번호를 잘못 작성하셨습니다.");
        }

        if(duplicateSName(username)) {
            return new LoginResponseDto(HttpStatus.BAD_REQUEST,"이름이 중복되셨습니다.");
        }

        User user = new User(username,password);
        userRepository.save(user);

        return new LoginResponseDto(HttpStatus.OK,"회원 가입을 성공하셨습니다.");
    }

    private boolean existUsername(Optional<User> user) {
        try {
            if(user.isEmpty()) {
                throw new IllegalArgumentException("해당 유저가 없습니다.");
            }
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    private boolean comparePassword(String repositoryPassword,String password) {
        try {
            if(!repositoryPassword.equals(password)) {
                throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
            }
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    private boolean invalidateUsername(String username) {
        try {
            if(!Invalidate.userLengthValidate(username)) {
                throw new IllegalArgumentException("최소 4자 이상, 10자 이하, 알파벳 소문자와 숫자로 이름을 구성해야합니다.");
            }
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    private boolean invalidatePassword(String password) {
        try {
            if(!Invalidate.passwordLengthValidate(password)) {
                throw new IllegalArgumentException("최소 8자 이상, 15자 이하, 알파벳 대소문자와 숫자로 번호를 구성해야합니다.");
            }
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    private boolean duplicateSName(String username) {
        try {
            if(Invalidate.duplicateUserName(userRepository.findByUsername(username))) {
                throw new IllegalArgumentException("중복된 사용자 이름이 존재합니다.");
            }
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            return true;
        }
        return false;
    }

}
