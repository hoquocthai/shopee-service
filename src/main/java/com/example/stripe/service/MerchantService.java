package com.example.stripe.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;

import com.example.stripe.service.dto.request.MerchantRequestDto;
import com.example.stripe.service.dto.response.MerchantResponseDto;

public interface MerchantService {

    MerchantResponseDto registerMerchant(MerchantRequestDto dto, BindingResult bindingResult,
            HttpServletRequest httpServletRequest);

    MerchantResponseDto getMerchant(HttpServletRequest httpServletRequest);

    Boolean checkIsMerchant(HttpServletRequest httpServletRequest);

    List<MerchantResponseDto> getAllMerchant();

}
