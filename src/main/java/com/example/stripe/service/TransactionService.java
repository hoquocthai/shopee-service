package com.example.stripe.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.example.stripe.service.dto.response.TransactionResponseDto;

public interface TransactionService {

    List<TransactionResponseDto> getAllPrivateTransaction(HttpServletRequest httpServletRequest);

}
