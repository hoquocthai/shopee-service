package com.example.stripe.security;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.stripe.security.dto.JwtTokenDto;
import com.example.stripe.service.RedisService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private static final String X_USER_ID = "X-User-Id";

    private final JwtProvider jwtProvider;
    private final RedisService redisService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest httpServletRequest,
                                    @NonNull HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        resolveTokenFromHeader(httpServletRequest)
            .map(jwtProvider::parseToken)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .filter(this::isTokenNotInBlacklist)
            .ifPresent(tokenDto -> {
                SecurityContextHolder.getContext().setAuthentication(createAuthentication(tokenDto));
                httpServletRequest.setAttribute(X_USER_ID, tokenDto.getUserId());
            });

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private Optional<String> resolveTokenFromHeader(HttpServletRequest httpServletRequest) {
        String authorization = httpServletRequest.getHeader("Authorization");
        if (StringUtils.isBlank(authorization) || !authorization.contains("Bearer")) {
            return Optional.empty();
        }
        return Optional.of(authorization.substring(7));
    }

    private UsernamePasswordAuthenticationToken createAuthentication(JwtTokenDto jwtTokenDto) {
        String principal = jwtTokenDto.getEmail();
        String credential = jwtTokenDto.getTokenId();
        List<SimpleGrantedAuthority> authorities = List.of(
            new SimpleGrantedAuthority("ROLE_" + jwtTokenDto.getRole())
        );
        return new UsernamePasswordAuthenticationToken(principal, credential, authorities);
    }

    public boolean isTokenNotInBlacklist(JwtTokenDto jwtTokenDto) {
        String key = String.format("blacklist:session:%s", jwtTokenDto.getTokenId());
        Boolean exist = redisService.getData(key, Boolean.class);
        return !Boolean.TRUE.equals(exist);
    }
}
