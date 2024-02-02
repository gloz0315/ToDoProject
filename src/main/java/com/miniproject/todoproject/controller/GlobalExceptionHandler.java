package com.miniproject.todoproject.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.miniproject.todoproject.dto.ResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(IllegalArgumentException.class)
	protected ResponseEntity<ResponseDto<?>> handleIllegalException(IllegalArgumentException e) {
		return ResponseEntity.badRequest().body(new ResponseDto<>(HttpStatus.BAD_REQUEST, e.getMessage(), null));
	}
}
