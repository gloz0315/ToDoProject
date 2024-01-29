package com.miniproject.todoproject.service;

import com.miniproject.todoproject.dto.SignupRequestDto;
import com.miniproject.todoproject.entity.User;
import com.miniproject.todoproject.entity.UserRoleEnum;
import com.miniproject.todoproject.invalidate.Invalidate;
import com.miniproject.todoproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        if(!Invalidate.userLengthValidate(username)) {
            throw new IllegalArgumentException("최소 4자 이상, 10자 이하, 알파벳 소문자와 숫자로 이름을 구성해야합니다.");
        }

        if(!Invalidate.passwordLengthValidate(password)) {
            throw new IllegalArgumentException("최소 8자 이상, 15자 이하, 알파벳 대소문자와 숫자로 번호를 구성해야합니다.");
        }

        Optional<User> checkUsername = userRepository.findByUsername(username);
        if(checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 이름이 존재합니다.");
        }

        UserRoleEnum role = UserRoleEnum.USER;
        if(requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        password = passwordEncoder.encode(password); // 패스워드 암호화

        User user = new User(username,password,role);
        userRepository.save(user);
    }
}
