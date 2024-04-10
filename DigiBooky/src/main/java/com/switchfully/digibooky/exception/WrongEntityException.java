package com.switchfully.digibooky.exception;

import org.springframework.http.HttpStatus;

public class WrongEntityException extends DigiBookyException {
    public WrongEntityException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
