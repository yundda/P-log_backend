package com.example.plog.service.exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message){
        super(message);
    }
}
