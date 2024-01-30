package com.miniproject.todoproject.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ResponseDto {
    private HttpStatus status;
    private String message;


    public ResponseDto(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
