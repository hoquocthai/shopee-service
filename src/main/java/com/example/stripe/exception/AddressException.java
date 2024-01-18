package com.example.stripe.exception;

public class AddressException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public AddressException(String mess) {
      super(mess);
  }
}
