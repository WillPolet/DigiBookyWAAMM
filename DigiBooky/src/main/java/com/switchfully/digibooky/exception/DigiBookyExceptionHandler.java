package com.switchfully.digibooky.exception;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class DigiBookyExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(DigiBookyExceptionHandler.class);
    @ExceptionHandler(DigiBookyException.class)
    protected ResponseEntity<ExceptionDto> exceptionFound(DigiBookyException ex) {
        logger.error(ex.getMessage(), ex);
        return ResponseEntity.status(ex.getStatusCode()).body(new ExceptionDto(ex.getMessage()));
    }


    protected ResponseEntity<ExceptionDto> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
//        return ResponseEntity.status(ex.getStatusCode()).body(new ExceptionDto(ex.getMessage()));
//        Map<String, String> errors = new HashMap<>();
        logger.error(ex.getBindingResult().getAllErrors().getLast().toString());
//        System.out.println(ex.getBindingResult().
//                getAllErrors().getLast());

    }


//                stream()
//                        .filter(error -> error.get)
//                forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//        return ResponseEntity.badRequest().body(errors);
//    }

}
