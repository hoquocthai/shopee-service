package com.example.stripe.controller.publics;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.stripe.service.WalletService;
import com.example.stripe.service.dto.request.BalanceRequestDto;
import com.example.stripe.service.dto.request.WalletRequestDto;
import com.example.stripe.service.dto.response.WalletResponseDto;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wallets")
public class WalletController {

    private final WalletService walletService;

    @ApiOperation(value = "API to get wallet")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @GetMapping(produces = "application/vn.shopee.api-v1+json")
    public ResponseEntity<WalletResponseDto> getWallet(@NonNull HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(walletService.getWallet(httpServletRequest));
    }

    @ApiOperation(value = "API to create wallet")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @PostMapping(produces = "application/vn.shopee.api-v1+json")
    public ResponseEntity<WalletResponseDto> createWallet(@RequestBody WalletRequestDto dto) {
        return ResponseEntity.ok(walletService.createWallet(dto));
    }

    @ApiOperation(value = "API to update wallet balance")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @PutMapping(produces = "application/vn.shopee.api-v1+json")
    public ResponseEntity<Void> updateWalletBalance(@NonNull HttpServletRequest httpServletRequest,
            @RequestBody BalanceRequestDto dto) {
        walletService.updateWalletBalance(httpServletRequest, dto);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "API to delete wallet")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @DeleteMapping(value = "/{id}", produces = "application/vn.shopee.api-v1+json")
    public ResponseEntity<Void> deleteWallet(@PathVariable Long id) {
        walletService.deleteWallet(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "API to check wallet existed")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @GetMapping(value = "/check-exist", produces = "application/vn.shopee.api-v1+json")
    public ResponseEntity<Boolean> checkWalletExisted(@NonNull HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(walletService.checkWalletExisted(httpServletRequest));
    }
}
