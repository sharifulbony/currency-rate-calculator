package com.shariful.kn.task.exceptions;

public class InvalidInputException extends RuntimeException{
    public InvalidInputException(String errorMessage){
        super(errorMessage);
    }
}
