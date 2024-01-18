package com.example.stripe.exception;

public class TransactionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TransactionException(String mess) {
        super(mess);
    }
}
