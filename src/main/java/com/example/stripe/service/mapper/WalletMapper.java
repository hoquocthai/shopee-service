package com.example.stripe.service.mapper;

import org.springframework.stereotype.Service;

import com.example.stripe.entity.Coin;
import com.example.stripe.entity.User;
import com.example.stripe.entity.Wallet;
import com.example.stripe.exception.UserException;
import com.example.stripe.exception.WalletException;
import com.example.stripe.repository.UserRepository;
import com.example.stripe.repository.WalletRepository;
import com.example.stripe.service.dto.CoinDto;
import com.example.stripe.service.dto.request.WalletRequestDto;
import com.example.stripe.service.dto.response.WalletResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletMapper {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    public Wallet toEntity(WalletRequestDto dto, Coin coin) {
        if (walletRepository.existsByUserId(dto.getUserId())) {
            throw new WalletException("Wallet is existed");
        }
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new UserException("User is not found"));

        return Wallet.builder()
                .balance(dto.getBalance())
                .blockedBalance(dto.getBlockedBalance())
                .coin(coin)
                .user(user)
                .build();
    }

    public WalletResponseDto toDto(Wallet wallet) {

        CoinDto coinDto = CoinDto.builder()
                .name(wallet.getCoin().getName())
                .shortName(wallet.getCoin().getShortName())
                .build();

        return WalletResponseDto.builder()
                .id(wallet.getId())
                .balance(wallet.getBalance())
                .blockedBalance(wallet.getBlockedBalance())
                .coin(coinDto)
                .userId(wallet.getUser().getId())
                .build();
    }
}
