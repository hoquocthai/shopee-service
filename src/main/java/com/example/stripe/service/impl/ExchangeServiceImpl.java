package com.example.stripe.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.example.stripe.entity.UserSession;
import com.example.stripe.entity.Wallet;
import com.example.stripe.exception.WalletException;
import com.example.stripe.repository.UserSessionRepository;
import com.example.stripe.repository.WalletRepository;
import com.example.stripe.service.ExchangeService;
import com.example.stripe.service.dto.response.ExchangeResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {

  private final WalletRepository walletRepository;
  private final UserSessionRepository sessionRepository;

  @Override
  public ExchangeResponseDto getExchangeRate(HttpServletRequest httpServletRequest) {
    String authorizationToken = httpServletRequest.getHeader("Authorization").substring(7);
    UserSession userSession = sessionRepository.findByToken(authorizationToken).get();
    Wallet wallet = walletRepository.findByUserId(userSession.getUser().getId())
        .orElseThrow(() -> new WalletException("Wallet is not found"));
    return ExchangeResponseDto.builder()
        .ExchangeRate(wallet.getCoin().getExchangeRate())
        .build();
  }
}
