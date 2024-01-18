package com.example.stripe.service.mapper;

import org.springframework.stereotype.Service;

import com.example.stripe.entity.Transaction;
import com.example.stripe.service.dto.response.TransactionResponseDto;
import org.springframework.beans.factory.annotation.Value;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionMapper {
    private final WalletMapper walletMapper;
    private final OrderMapper orderMapper;

    @Value("${application.gateway.url}")
    private String gatewayUrl;

    public TransactionResponseDto toDto(Transaction transaction) {
        return TransactionResponseDto.builder()
                .id(transaction.getId())
                .walletId(transaction.getWallet().getId())
                .orderInfo(orderMapper.toDto(transaction.getOrders(),
                        gatewayUrl + "qr-code/" + transaction.getOrders().getId().toString()))
                .type(transaction.getType())
                .credit(transaction.getCredit())
                .amount(transaction.getAmount())
                .build();
    }
}
