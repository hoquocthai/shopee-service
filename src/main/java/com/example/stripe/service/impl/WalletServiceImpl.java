package com.example.stripe.service.impl;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.stripe.entity.Coin;
import com.example.stripe.entity.UserSession;
import com.example.stripe.entity.Wallet;
import com.example.stripe.exception.WalletException;
import com.example.stripe.repository.CoinRepository;
import com.example.stripe.repository.UserSessionRepository;
import com.example.stripe.repository.WalletRepository;
import com.example.stripe.service.WalletService;
import com.example.stripe.service.dto.request.BalanceRequestDto;
import com.example.stripe.service.dto.request.WalletRequestDto;
import com.example.stripe.service.dto.response.WalletResponseDto;
import com.example.stripe.service.mapper.CoinMapper;
import com.example.stripe.service.mapper.WalletMapper;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;
    private final CoinMapper coinMapper;
    private final CoinRepository coinRepository;
    private final UserSessionRepository sessionRepository;

    @Override
    @Transactional
    public WalletResponseDto createWallet(WalletRequestDto dto) {
        Optional<Coin> coinOpt = coinRepository.findByName(dto.getCoin().getName());
        if (coinOpt.isEmpty()) {
            coinRepository.save(coinMapper.toEntity(dto.getCoin()));
            coinOpt = coinRepository.findByName(dto.getCoin().getName());
        }
        Wallet newWallet = walletRepository.save(walletMapper.toEntity(dto, coinOpt.get()));
        return walletMapper.toDto(newWallet);
    }

    @Override
    public WalletResponseDto getWallet(HttpServletRequest httpServletRequest) {
        String authorizationToken = httpServletRequest.getHeader("Authorization").substring(7);
        UserSession userSession = sessionRepository.findByToken(authorizationToken).get();
        Wallet wallet = walletRepository.findByUserId(userSession.getUser().getId())
                .orElseThrow(() -> new WalletException("Wallet is not found"));
        return walletMapper.toDto(wallet);
    }

    @Override
    @Transactional
    public void updateWalletBalance(HttpServletRequest httpServletRequest, BalanceRequestDto dto) {
        String authorizationToken = httpServletRequest.getHeader("Authorization").substring(7);
        UserSession userSession = sessionRepository.findByToken(authorizationToken).get();

        Wallet wallet = walletRepository.findByUserId(userSession.getUser().getId())
                .orElseThrow(() -> new WalletException("Wallet is not found"));
        wallet.setBalance(dto.getBalance());
        wallet.setBlockedBalance(dto.getBlockedBalance());
    }

    @Override
    @Transactional
    public void deleteWallet(Long id) {
        walletRepository.deleteById(id);
    }

    @Override
    public boolean checkWalletExisted(@NonNull HttpServletRequest httpServletRequest) {
        String authorizationToken = httpServletRequest.getHeader("Authorization").substring(7);
        UserSession userSession = sessionRepository.findByToken(authorizationToken).get();
        Optional<Wallet> walletOpt = walletRepository.findByUserId(userSession.getUser().getId());
        if (walletOpt.isPresent())
            return true;
        return false;
    }
}
