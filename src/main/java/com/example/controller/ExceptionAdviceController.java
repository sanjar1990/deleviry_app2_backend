package com.example.controller;

import com.example.exceptions.AppBadException;
import com.example.exceptions.ItemNotFoundException;
import com.example.exceptions.UnAuthorizedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdviceController {
    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<String>handler(ItemNotFoundException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<String> handler(UnAuthorizedException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    @ExceptionHandler(AppBadException.class)
    public ResponseEntity<String> handler(AppBadException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
