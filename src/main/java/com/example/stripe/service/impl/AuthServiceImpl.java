package com.example.stripe.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.stripe.config.StripeProperties;
import com.example.stripe.entity.Coin;
import com.example.stripe.entity.User;
import com.example.stripe.entity.UserSession;
import com.example.stripe.entity.Wallet;
import com.example.stripe.exception.UnauthorizedException;
import com.example.stripe.exception.UserException;
import com.example.stripe.repository.CoinRepository;
import com.example.stripe.repository.UserRepository;
import com.example.stripe.repository.UserSessionRepository;
import com.example.stripe.repository.WalletRepository;
import com.example.stripe.security.JwtProvider;
import com.example.stripe.security.dto.JwtTokenDto;
import com.example.stripe.service.AuthService;
import com.example.stripe.service.RedisService;
import com.example.stripe.service.dto.ResetPasswordDto;
import com.example.stripe.service.dto.UserDto;
import com.example.stripe.service.dto.request.LoginDto;
import com.example.stripe.service.dto.request.SignupDto;
import com.example.stripe.service.dto.response.LoginResultDto;
import com.example.stripe.service.mapper.UserMapper;
import static com.example.stripe.constant.Constants.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    /*** Repositories */
    private final UserSessionRepository sessionRepository;
    private final UserRepository userRepository;

    /** Services */
    private final RedisService redisService;

    /** Others */
    private final StripeProperties properties;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtProvider jwtProvider;
    private final WalletRepository walletRepository;
    private final CoinRepository coinRepository;

    @Override
    @Transactional
    public String logout(HttpServletRequest httpServletRequest) {
        String authorizationToken = httpServletRequest.getHeader("Authorization").substring(7);
        Optional<UserSession> userSession = sessionRepository.findByToken(authorizationToken);
        if (userSession.isEmpty()) {
            return "fail";
        }
        sessionRepository.delete(userSession.get());
        pushSessionIdToBlacklist(userSession.get().getSessionId());
        return "success";
    }

    @Override
    @Transactional
    public LoginResultDto login(@Valid LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(UnauthorizedException::new);

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new UnauthorizedException();
        }

        JwtTokenDto tokenDto = jwtProvider.createToken(user);
        UserSession session = saveUserSession(user, tokenDto);
        return LoginResultDto.builder()
                .token(tokenDto.getToken())
                .sessionId(session.getSessionId()).build();
    }

    @Override
    @Transactional
    public UserDto signup(@Valid SignupDto signupDto) {
        Optional<User> userOpt = userRepository.findByEmail(signupDto.getEmail());
        if (userOpt.isPresent())
            throw new UserException("Account is existed");
        User user = new User();
        user.setEmail(signupDto.getEmail());
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        User user2 = userRepository.save(user);

        Optional<Coin> coinOpt = coinRepository.findByShortName(USDC_SHORTNAME);
        Coin coin = new Coin();

        if (coinOpt.isEmpty()) {
            coin = coinRepository.save(Coin.builder()
                    .name(USDC_NAME)
                    .shortName(USDC_SHORTNAME)
                    .exchangeRate(BigDecimal.ONE)
                    .build());
        } else
            coin = coinOpt.get();

        Wallet wallet = Wallet.builder()
                .balance(BigDecimal.ZERO)
                .blockedBalance(BigDecimal.ZERO)
                .user(user2)
                .coin(coin)
                .build();

        walletRepository.save(wallet);

        return userMapper.toDto(user2);
    }

    private UserSession saveUserSession(User user, JwtTokenDto tokenDto) {
        UserSession session = new UserSession(user, tokenDto);
        return sessionRepository.save(session);
    }

    private void pushSessionIdToBlacklist(String sessionId) {
        redisService.setData(String.format("blacklist:session:%s", sessionId), Boolean.TRUE,
                properties.getJwt().getTokenValidityInSeconds());
    }

    @Override
    @Transactional
    public Boolean resetPassword(@Valid ResetPasswordDto dto) {
        Optional<User> user = userRepository.findByEmail(dto.getEmail());
        if (user.isEmpty())
            return false;
        user.get().setPassword(passwordEncoder.encode(dto.getPassword()));
        return true;
    }
}
