package com.example.stripe.service.dto.request;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentPaypalRequestDto {

    private Instant createdDate;
    private String currency;
    private BigDecimal amount;
    private Long userId;
    private String status;
}
