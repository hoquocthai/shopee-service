package com.example.stripe.constant;

public class Constants {

    private Constants() {
    }

    public static final String X_SESSION_ID = "X-Session-Id";
    public static final String X_USER_ID = "X-User-Id";
    public static final String X_DESTINATION_URL = "X-Destination-Url";
    public static final String CHECKOUT_COMPLETED = "checkout.session.completed";
    public static final String CHARGE_SUCCESS = "charge.succeeded";
    public static final String PAYMENT_INTENT_SUCCESS = "payment_intent.succeeded";
    public static final String USDC_NAME = "USD Coin";
    public static final String USDC_SHORTNAME = "USDC";
}
