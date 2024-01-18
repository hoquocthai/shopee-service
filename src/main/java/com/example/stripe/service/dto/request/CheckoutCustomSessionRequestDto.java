package com.example.stripe.service.dto.request;

import com.example.stripe.service.dto.LineItemDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckoutCustomSessionRequestDto {
    private String successUrl;
    private String cancelUrl;
    private LineItemDto[] lineItems;
}
