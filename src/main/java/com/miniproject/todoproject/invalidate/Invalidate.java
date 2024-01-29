package com.miniproject.todoproject.invalidate;

import com.miniproject.todoproject.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class Invalidate {

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

    public static boolean duplicateUserName(Optional<User> userList) {
        return userList.isPresent();
    }

    private static boolean lowerAlphaNumeric(String text) {
        for(char c : text.toCharArray()) {
            if(!Character.isLowerCase(c) && !Character.isDigit(c)) {
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
