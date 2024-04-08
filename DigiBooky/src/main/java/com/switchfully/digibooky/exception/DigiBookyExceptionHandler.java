package com.switchfully.digibooky.exception;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

@ControllerAdvice
public class DigiBookyExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(DigiBookyExceptionHandler.class);
    @ExceptionHandler(DigiBookyException.class)
    protected ResponseEntity<ExceptionDto> exceptionFound(DigiBookyException ex) {
        logger.error(ex.getMessage(), ex);
        return ResponseEntity.status(ex.getStatusCode()).body(new ExceptionDto(ex.getMessage()));
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        StringJoiner sj = new StringJoiner(" ");
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            sj.add(((FieldError) error).getField() + ": " + error.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(new ExceptionDto(sj.toString()));
    }
}
