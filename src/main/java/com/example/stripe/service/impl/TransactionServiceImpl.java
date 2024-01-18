package com.example.stripe.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.example.stripe.entity.UserSession;
import com.example.stripe.repository.TransactionRepository;
import com.example.stripe.repository.UserSessionRepository;
import com.example.stripe.service.TransactionService;
import com.example.stripe.service.dto.response.TransactionResponseDto;
import com.example.stripe.service.mapper.TransactionMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final UserSessionRepository sessionRepository;

    @Override
    public List<TransactionResponseDto> getAllPrivateTransaction(HttpServletRequest httpServletRequest) {
        String authorizationToken = httpServletRequest.getHeader("Authorization").substring(7);
        UserSession userSession = sessionRepository.findByToken(authorizationToken).get();
        return transactionRepository.findAll().stream()
                .filter(val -> val.getWallet().getUser().getId() == userSession.getUser().getId())
                .map((e) -> transactionMapper.toDto(e))
                .collect(Collectors.toList());
    }
}
