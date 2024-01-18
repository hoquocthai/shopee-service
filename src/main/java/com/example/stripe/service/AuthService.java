package com.example.stripe.service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.example.stripe.service.dto.ResetPasswordDto;
import com.example.stripe.service.dto.UserDto;
import com.example.stripe.service.dto.request.LoginDto;
import com.example.stripe.service.dto.request.SignupDto;
import com.example.stripe.service.dto.response.LoginResultDto;

public interface AuthService {

    String logout(HttpServletRequest httpServletRequest);

    LoginResultDto login(@Valid LoginDto loginDto);

    UserDto signup(@Valid SignupDto signupDto);

    Boolean resetPassword(@Valid ResetPasswordDto dto);

}
