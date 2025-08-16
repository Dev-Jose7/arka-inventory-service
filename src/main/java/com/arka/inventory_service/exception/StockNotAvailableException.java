package com.arka.inventory_service.exception;

public class StockNotAvailableException extends RuntimeException {

    public StockNotAvailableException() {
        super("Stock not available");
    }

    public StockNotAvailableException(String message) {
        super(message);
    }
}
