package com.miniproject.todoproject.invalidate;

import com.miniproject.todoproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Invalidate {
    private final UserRepository repository;

    public static boolean userLengthValidate(String username) {
        if(username.length() < 4 || username.length() > 10) {
            return false;
        }

        return lowerAlphaNumeric(username);
    }

    public static boolean passwordLengthValidate(String password) {
        if(password.length() < 8 || password.length() > 15) {
            return false;
        }

        return alphaNumeric(password);
    }


    private static boolean lowerAlphaNumeric(String text) {
        for(char c : text.toCharArray()) {
            if(!(Character.isLowerCase(c)) || Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    private static boolean alphaNumeric(String text) {
        for(char c : text.toCharArray()) {
            if(!Character.isLetterOrDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
