package com.switchfully.digibooky.exception;

import org.springframework.http.HttpStatus;

public class UniqueFieldAlreadyExistException extends DigiBookyException {

    public UniqueFieldAlreadyExistException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

}
