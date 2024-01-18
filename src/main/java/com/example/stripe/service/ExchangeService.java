package com.example.stripe.service;

import javax.servlet.http.HttpServletRequest;

import com.example.stripe.service.dto.response.ExchangeResponseDto;

public interface ExchangeService {

  ExchangeResponseDto getExchangeRate(HttpServletRequest httpServletRequest);

}
