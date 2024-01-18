package com.example.stripe.service.dto.request;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalanceRequestDto {
    private BigDecimal balance;
    private BigDecimal blockedBalance;
}
