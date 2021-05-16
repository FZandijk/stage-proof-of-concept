package com.poc.orderservice.errorHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandlers {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        StringBuilder errorMessages = new StringBuilder();
        for (int i = 0; i < errors.size()-1; i++) {
            errorMessages
                    .append(errors.get(i).getDefaultMessage())
                    .append(System.lineSeparator());
        }
        errorMessages.append(errors.get(errors.size()-1).getDefaultMessage());
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }
}
