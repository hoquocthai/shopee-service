package com.example.stripe.service.dto.response;

import com.example.stripe.enums.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    private Long id;
    private OrderStatus status;
    private String expireTime;
    private String qrcodeLink;
    private String qrContent;
    private Long merchantId;
    private ProductResponseDto productInfo;
    private Number quantity;
    private Long userId;
    private Long addressId;
}
