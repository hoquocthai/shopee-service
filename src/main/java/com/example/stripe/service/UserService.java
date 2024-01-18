package com.example.stripe.service;

import javax.servlet.http.HttpServletRequest;

import com.example.stripe.service.dto.response.UserDetailsResponseDto;

public interface UserService {

    UserDetailsResponseDto getUserDetails(HttpServletRequest httpServletRequest);
}
