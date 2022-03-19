package com.example.pizzaservice.exceptions;

public class SizeException extends RuntimeException {
    public SizeException(String message) {
        super(message);
    }
}
