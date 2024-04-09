package com.switchfully.digibooky.exception;

import org.springframework.http.HttpStatus;

public class AlreadyLentException extends DigiBookyException {
    public AlreadyLentException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
