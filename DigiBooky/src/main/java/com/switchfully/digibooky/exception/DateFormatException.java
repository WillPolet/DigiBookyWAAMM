package com.switchfully.digibooky.exception;

import org.springframework.http.HttpStatus;

public class DateFormatException extends DigiBookyException {
    public DateFormatException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
