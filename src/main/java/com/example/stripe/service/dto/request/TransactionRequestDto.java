package com.example.stripe.service.dto.request;

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
public class TransactionRequestDto {

    private Long walletId;
    private Long orderId;
    private TransactionType type;
    private TransactionCredit credit;
    private BigDecimal amount;
}
