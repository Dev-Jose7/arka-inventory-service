package com.arka.inventory_service.exception;

public class InvalidTypeException extends RuntimeException{

    public InvalidTypeException(){
        super("Invalid type provided.");
    }

    public InvalidTypeException(String message){
        super(message);
    }
}
