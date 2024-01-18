package com.example.stripe.service.dto.response;

import java.math.BigDecimal;

import com.example.stripe.enums.TransactionCredit;
import com.example.stripe.enums.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponseDto {

    private Long id;
    private Long walletId;
    private OrderResponseDto orderInfo;
    private TransactionType type;
    private TransactionCredit credit;
    private BigDecimal amount;
}