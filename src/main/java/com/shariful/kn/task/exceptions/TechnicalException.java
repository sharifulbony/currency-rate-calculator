package com.shariful.kn.task.exceptions;

public class TechnicalException extends RuntimeException{
    public TechnicalException(String errorMessage){
        super(errorMessage);
    }
}
