package com.example.stripe.service.dto.request;

import java.math.BigDecimal;

import com.example.stripe.service.dto.CoinDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletRequestDto {
    private Long userId;
    private BigDecimal balance;
    private BigDecimal blockedBalance;
    private CoinDto coin;
}
