package com.thunder.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
//	@ExceptionHandler(DuplicateUserIdException.class)
//    public ResponseEntity<String> handleDuplicateUserIdException(DuplicateUserIdException ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
//    }
}
