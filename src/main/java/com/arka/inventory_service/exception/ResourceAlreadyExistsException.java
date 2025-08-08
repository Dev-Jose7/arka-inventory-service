package com.arka.inventory_service.exception;

public class ResourceAlreadyExistsException extends RuntimeException{

    public ResourceAlreadyExistsException(){
        super("Resource already exists.");
    }

    public ResourceAlreadyExistsException(String message){
        super(message);
    }
}
