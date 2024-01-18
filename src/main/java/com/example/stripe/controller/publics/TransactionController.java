package com.example.stripe.controller.publics;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.stripe.service.TransactionService;
import com.example.stripe.service.dto.response.TransactionResponseDto;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @ApiOperation(value = "API to get all private transaction")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @GetMapping(produces = "application/vn.shopee.api-v1+json")
    public ResponseEntity<List<TransactionResponseDto>> getAllPrivateTransaction(
            @NonNull HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(transactionService.getAllPrivateTransaction(httpServletRequest));
    }

}
