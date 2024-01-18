package com.example.stripe.controller.publics;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.stripe.service.MerchantService;
import com.example.stripe.service.dto.request.MerchantRequestDto;
import com.example.stripe.service.dto.response.MerchantResponseDto;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/merchants")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService service;

    @ApiOperation(value = "API to register merchant")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @PostMapping(produces = "application/vn.shopee.api-v1+json")
    public ResponseEntity<MerchantResponseDto> registerMerchant(@Valid @RequestBody MerchantRequestDto dto,
            BindingResult bindingResult, @NonNull HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(service.registerMerchant(dto, bindingResult, httpServletRequest));
    }

    @ApiOperation(value = "API to get merchant")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @GetMapping(produces = "application/vn.shopee.api-v1+json")
    public ResponseEntity<MerchantResponseDto> getMerchant(@NonNull HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(service.getMerchant(httpServletRequest));
    }

    @ApiOperation(value = "API to check user is a merchant")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @GetMapping(value = "/check", produces = "application/vn.shopee.api-v1+json")
    public ResponseEntity<Boolean> checkIsMerchant(@NonNull HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(service.checkIsMerchant(httpServletRequest));
    }
}
