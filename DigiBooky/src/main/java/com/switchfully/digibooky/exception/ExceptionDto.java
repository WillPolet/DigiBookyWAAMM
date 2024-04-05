package com.switchfully.digibooky.exception;

public class ExceptionDto {
    private String message;

    public ExceptionDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
