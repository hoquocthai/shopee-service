package com.example.stripe.service.mapper;

import org.springframework.stereotype.Service;

import com.example.stripe.entity.Coin;
import com.example.stripe.service.dto.CoinDto;

@Service
public class CoinMapper {
	public Coin toEntity(CoinDto dto) {
		return Coin.builder()
				.name(dto.getName())
				.shortName(dto.getShortName())
				.build();
	}

	public CoinDto toDto(Coin coin) {
		return CoinDto.builder()
				.name(coin.getName())
				.shortName(coin.getShortName())
				.build();
	}
}
