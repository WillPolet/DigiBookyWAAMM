package com.switchfully.digibooky.exception;

import org.springframework.http.HttpStatus;

public class AccessForbiddenException extends DigiBookyException{
    public AccessForbiddenException (String message){
        super(message, HttpStatus.FORBIDDEN);
    }
}
