package com.example.stripe.exception;

public class OrderException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public OrderException(String mess) {
        super(mess);
    }
}
