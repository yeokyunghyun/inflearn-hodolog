package com.example.blog.controller;

import com.example.blog.exception.HodologException;
import com.example.blog.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * packageName    : com.example.blog.controller
 * fileName       :
 * author         : njy
 * date           : 2024-10-02
 * description    : <<여기 설명>>
 * <pre>
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-10-02           njy            최초 생성
 * </pre>
 */

@RestControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse invalidExceptionHandler(MethodArgumentNotValidException e) {
//            FieldError fieldError = e.getFieldError();
//            String field = fieldError.getField();
//            String message = fieldError.getDefaultMessage();

        ErrorResponse response = ErrorResponse.builder()
            .code("400")
            .message("잘못된 요청입니다.")
            .build();

        for(FieldError fieldError: e.getFieldErrors()) {
            response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return response;
    }


    @ResponseBody
//    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(HodologException.class)
    public ResponseEntity<ErrorResponse> postNotFound(HodologException e) {
        int statusCode = e.getStatusCode();

        ErrorResponse response = ErrorResponse.builder()
            .code(String.valueOf(statusCode))
            .message(e.getMessage())
            .validation(e.getValidation())
            .build();

        return ResponseEntity.status(statusCode)
            .body(response);
    }
}