package com.example.stripe.service.dto.request;

import com.example.stripe.service.dto.ProductPaymentDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {

    private Long merchantId;

    private Long userId;

    private ProductPaymentDto productInformation;

    private Long addressId;
}
