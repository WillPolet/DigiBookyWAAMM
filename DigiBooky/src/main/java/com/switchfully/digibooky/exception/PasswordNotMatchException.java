package com.switchfully.digibooky.exception;

import org.springframework.http.HttpStatus;

public class PasswordNotMatchException extends DigiBookyException{
    public PasswordNotMatchException (String message){
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
