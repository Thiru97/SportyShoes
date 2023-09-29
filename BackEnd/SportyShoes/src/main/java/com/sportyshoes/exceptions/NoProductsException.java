package com.sportyshoes.exceptions;


public class NoProductsException extends RuntimeException{
    public NoProductsException(String message) {
        super(message);
    }
}
