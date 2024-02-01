package com.miniproject.todoproject.invalidate;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.miniproject.todoproject.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class Invalidate {

	public static boolean userLengthValidate(String username) {
		if (username.length() < 4 || username.length() > 10) {
			return false;
		}
		return lowerAlphaNumeric(username);
	}

	public static boolean passwordLengthValidate(String password) {
		if (password.length() < 8 || password.length() > 15) {
			return false;
		}
		return alphaNumeric(password);
	}

	public static boolean duplicateUserName(Optional<User> userList) {
		return userList.isPresent();
	}

	private static boolean lowerAlphaNumeric(String text) {
		int alpha = 0;
		int number = 0;
		for (char c : text.toCharArray()) {
			if (!Character.isLowerCase(c) && !Character.isDigit(c)) {
				return false;
			}

			if (Character.isDigit(c))
				number++;
			else if (Character.isLowerCase(c))
				alpha++;
		}

		return alpha != 0 && number != 0;
	}

	private static boolean alphaNumeric(String text) {
		int bigAlpha = 0;
		int smallAlpha = 0;
		int number = 0;
		for (char c : text.toCharArray()) {
			if (!Character.isLetterOrDigit(c)) {
				return false;
			}

			if (Character.isDigit(c))
				number++;
			else if (Character.isLowerCase(c))
				smallAlpha++;
			else if (Character.isUpperCase(c))
				bigAlpha++;
		}

		return bigAlpha != 0 && smallAlpha != 0 && number != 0;
	}
}
