package com.arka.inventory_service.exception;

public class EmailSendException extends RuntimeException{

    public EmailSendException(){
        super("Failed to send email");
    }

    public EmailSendException(String message){
        super(message);
    }
}
