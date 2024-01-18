package com.example.stripe.service.dto.request;

import com.example.stripe.service.dto.StripeProductDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutSessionRequestDto {
    private String successUrl;
    private String cancelUrl;
    private StripeProductDto[] lineItems;
}
