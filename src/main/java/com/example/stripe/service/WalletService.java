package com.example.stripe.service;

import javax.servlet.http.HttpServletRequest;

import com.example.stripe.service.dto.request.BalanceRequestDto;
import com.example.stripe.service.dto.request.WalletRequestDto;
import com.example.stripe.service.dto.response.WalletResponseDto;

import lombok.NonNull;

public interface WalletService {

    WalletResponseDto createWallet(WalletRequestDto dto);

    WalletResponseDto getWallet(HttpServletRequest httpServletRequest);

    void updateWalletBalance(HttpServletRequest httpServletRequest, BalanceRequestDto dto);

    void deleteWallet(Long id);

    boolean checkWalletExisted(@NonNull HttpServletRequest httpServletRequest);
}
