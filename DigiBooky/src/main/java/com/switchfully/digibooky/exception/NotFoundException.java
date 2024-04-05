package com.switchfully.digibooky.exception;


import org.springframework.http.HttpStatus;

public class NotFoundException extends DigiBookyException{
    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
