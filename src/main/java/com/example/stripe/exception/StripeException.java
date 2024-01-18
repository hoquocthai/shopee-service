package com.example.stripe.exception;

public class StripeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public StripeException(String mess) {
        super(mess);
    }
}
