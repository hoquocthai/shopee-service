package com.example.stripe.service;

import javax.servlet.http.HttpServletRequest;

import com.example.stripe.service.dto.request.CheckoutCustomSessionRequestDto;
import com.example.stripe.service.dto.request.CheckoutSessionRequestDto;
import com.example.stripe.service.dto.request.PaymentIntentRequestDto;
import com.example.stripe.service.dto.request.PaymentPaypalRequestDto;
import com.example.stripe.service.dto.response.CheckoutStatusResponse;
import com.example.stripe.service.dto.response.PaySessionResponseDto;
import com.example.stripe.service.dto.response.PaymentIntentResponseDto;
import com.example.stripe.service.dto.response.PaymentStatusInfo;

import lombok.NonNull;

public interface PaymentService {
    PaySessionResponseDto createSession(CheckoutSessionRequestDto dto);

    PaySessionResponseDto createCustomSession(HttpServletRequest httpServletRequest,
            CheckoutCustomSessionRequestDto dto);

    PaymentStatusInfo checkCustomSessionStatus(String dto);

    CheckoutStatusResponse payment(Long orderId, @NonNull HttpServletRequest httpServletRequest);

    PaymentIntentResponseDto createPaymentIntent(HttpServletRequest httpServletRequest, PaymentIntentRequestDto dto);

    Boolean createPaymentPaypal(PaymentPaypalRequestDto dto);
}