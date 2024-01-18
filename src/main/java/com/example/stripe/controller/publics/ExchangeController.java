package com.example.stripe.controller.publics;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.stripe.service.ExchangeService;
import com.example.stripe.service.dto.response.ExchangeResponseDto;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/exchange")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;

    @ApiOperation(value = "API to get exchange rate")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @GetMapping(value = "/get-rate", produces = "application/vn.shopee.api-v1+json")
    public ResponseEntity<ExchangeResponseDto> getExchangeRate(@NonNull HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(exchangeService.getExchangeRate(httpServletRequest));
    }
}
