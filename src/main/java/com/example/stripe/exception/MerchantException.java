package com.example.stripe.exception;

public class MerchantException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MerchantException(String mess) {
        super(mess);
    }
}
