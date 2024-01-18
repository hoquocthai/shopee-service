package com.example.stripe.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.stripe.entity.UserSession;
import com.example.stripe.exception.UserException;
import com.example.stripe.repository.UserSessionRepository;
import com.example.stripe.service.UserService;
import com.example.stripe.service.dto.response.UserDetailsResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserSessionRepository userSessionRepository;

    @Override
    public UserDetailsResponseDto getUserDetails(HttpServletRequest httpServletRequest) {
        String authorizationToken = httpServletRequest.getHeader("Authorization").substring(7);
        UserSession userSession = userSessionRepository.findByToken(authorizationToken)
                .orElseThrow(() -> new UserException("user session is not found"));

        return UserDetailsResponseDto.builder()
                .sessionId(userSession.getSessionId())
                .userId(userSession.getUser().getId())
                .email(userSession.getUser().getEmail())
                .build();
    }
}
