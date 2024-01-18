package com.example.stripe.controller.publics;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.stripe.service.PaymentService;
import com.example.stripe.service.dto.request.CheckoutCustomSessionRequestDto;
import com.example.stripe.service.dto.request.CheckoutSessionRequestDto;
import com.example.stripe.service.dto.request.PaymentIntentRequestDto;
import com.example.stripe.service.dto.request.PaymentPaypalRequestDto;
import com.example.stripe.service.dto.response.CheckoutStatusResponse;
import com.example.stripe.service.dto.response.PaySessionResponseDto;
import com.example.stripe.service.dto.response.PaymentIntentResponseDto;
import com.example.stripe.service.dto.response.PaymentStatusInfo;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

        private final PaymentService paymentService;

        @ApiOperation(value = "API to create stripe session")
        @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
                        @ApiResponse(code = 500, message = "Internal server error") })
        @PostMapping(value = "/checkout-session", produces = "application/vn.shopee.api-v1+json")
        public ResponseEntity<PaySessionResponseDto> createSession(@RequestBody CheckoutSessionRequestDto dto) {
                return ResponseEntity.ok(paymentService.createSession(dto));
        }

        @ApiOperation(value = "API to create stripe custom session")
        @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
                        @ApiResponse(code = 500, message = "Internal server error") })
        @PostMapping(value = "/custom-checkout-session", produces = "application/vn.shopee.api-v1+json")
        public ResponseEntity<PaySessionResponseDto> createCustomSession(@NonNull HttpServletRequest httpServletRequest,
                        @RequestBody CheckoutCustomSessionRequestDto dto) {
                return ResponseEntity.ok(paymentService.createCustomSession(httpServletRequest, dto));
        }

        @ApiOperation(value = "API to check stripe custom status")
        @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
                        @ApiResponse(code = 500, message = "Internal server error") })
        @GetMapping(value = "/check/{id}", produces = "application/vn.shopee.api-v1+json")
        public ResponseEntity<PaymentStatusInfo> checkCustomSessionStatus(@PathVariable String id) {
                return ResponseEntity.ok(paymentService.checkCustomSessionStatus(id));
        }

        @ApiOperation(value = "API to payment")
        @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
                        @ApiResponse(code = 500, message = "Internal server error") })
        @GetMapping(value = "/{orderId}", produces = "application/vn.shopee.api-v1+json")
        public ResponseEntity<CheckoutStatusResponse> payment(@PathVariable Long orderId,
                        @NonNull HttpServletRequest httpServletRequest) {
                return ResponseEntity.ok(paymentService.payment(orderId, httpServletRequest));
        }

        @ApiOperation(value = "API to create payment intent")
        @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
                        @ApiResponse(code = 500, message = "Internal server error") })
        @PostMapping(value = "/create-payment-intent", produces = "application/vn.shopee.api-v1+json")
        public ResponseEntity<PaymentIntentResponseDto> createPaymentIntent(
                        @NonNull HttpServletRequest httpServletRequest,
                        @RequestBody PaymentIntentRequestDto dto) {
                return ResponseEntity.ok(paymentService.createPaymentIntent(httpServletRequest, dto));
        }

        @ApiOperation(value = "API to create payment paypal")
        @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
                        @ApiResponse(code = 500, message = "Internal server error") })
        @PostMapping(value = "/paypal", produces = "application/vn.shopee.api-v1+json")
        public ResponseEntity<Boolean> createPaymentPaypal(
                        @RequestBody PaymentPaypalRequestDto dto) {
                return ResponseEntity.ok().body(paymentService.createPaymentPaypal(dto));
        }
}