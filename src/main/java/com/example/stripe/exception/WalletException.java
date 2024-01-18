package com.example.stripe.exception;

public class WalletException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public WalletException(String mess) {
        super(mess);
    }
}
