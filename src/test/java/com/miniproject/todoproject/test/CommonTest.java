package com.miniproject.todoproject.test;

import com.miniproject.todoproject.entity.User;

public interface CommonTest {

	String ANOTHER_PREFIX = "another-";
	Long TEST_USER_ID = 1L;
	Long TEST_ANOTHER_USER_ID = 2L;
	String TEST_USER_NAME = "username";
	String TEST_USER_PASSWORD = "password";
	User TEST_USER = new User(TEST_USER_NAME, TEST_USER_PASSWORD);

	User TEST_ANOTHER_USER = new User(ANOTHER_PREFIX + TEST_USER_NAME, ANOTHER_PREFIX + TEST_USER_PASSWORD);
}
