package com.shopbackend.shoppingcard.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {

        super(message);
    }
}
