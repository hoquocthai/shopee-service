package com.example.stripe.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.stripe.entity.UserSession;
import com.example.stripe.repository.UserSessionRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.example.stripe.entity.Merchant;
import com.example.stripe.entity.User;
import com.example.stripe.exception.MerchantException;
import com.example.stripe.exception.UserException;
import com.example.stripe.repository.MerchantRepository;
import com.example.stripe.repository.UserRepository;
import com.example.stripe.service.MerchantService;
import com.example.stripe.service.dto.request.MerchantRequestDto;
import com.example.stripe.service.dto.response.MerchantResponseDto;
import com.example.stripe.service.mapper.MerchantMapper;

import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MerchantServiceImpl implements MerchantService {

    private final MerchantRepository merchantRepository;
    private final UserRepository userRepository;
    private final MerchantMapper merchantMapper;
    private final UserSessionRepository sessionRepository;

    @Override
    @Transactional
    public MerchantResponseDto registerMerchant(MerchantRequestDto dto, BindingResult bindingResult,
            @NonNull HttpServletRequest httpServletRequest) {

        String authorizationToken = httpServletRequest.getHeader("Authorization").substring(7);
        UserSession userSession = sessionRepository.findByToken(authorizationToken)
                .orElseThrow(() -> new UserException("User session is not found"));

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            String errorMsg = "";
            for (String key : errors.keySet()) {
                errorMsg += "Error at: " + key + ", Reason: " + errors.get(key) + "\n";
            }
            throw new MerchantException(errorMsg);
        }

        User user = userRepository.findById(userSession.getUser().getId())
                .orElseThrow(() -> new UserException("User is not found"));

        Merchant newMerchant = merchantMapper.toEntity(dto, user);

        return merchantMapper.toDto(merchantRepository.save(newMerchant));
    }

    @Override
    public MerchantResponseDto getMerchant(HttpServletRequest httpServletRequest) {
        String authorizationToken = httpServletRequest.getHeader("Authorization").substring(7);
        UserSession userSession = sessionRepository.findByToken(authorizationToken).get();

        Merchant merchant = merchantRepository.findByUserId(userSession.getUser().getId())
                .orElseThrow(() -> new MerchantException("Merchant is not found"));
        return merchantMapper.toDto(merchant);
    }

    @Override
    public Boolean checkIsMerchant(HttpServletRequest httpServletRequest) {
        String authorizationToken = httpServletRequest.getHeader("Authorization").substring(7);
        UserSession userSession = sessionRepository.findByToken(authorizationToken).get();

        Optional<Merchant> merchantOpt = merchantRepository.findByUserId(userSession.getUser().getId());
        if (merchantOpt.isPresent())
            return true;
        return false;
    }

    @Override
    public List<MerchantResponseDto> getAllMerchant() {
        List<Merchant> merchants = merchantRepository.findAll();
        List<MerchantResponseDto> dtos = merchants.stream().map((e) -> merchantMapper.toDto(e))
                .collect(Collectors.toList());
        return dtos;
    }
}
