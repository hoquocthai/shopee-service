package com.example.stripe.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceRequestDto {
    private String currency;
    private Long unitAmount;
    private String product;
    private String nickName;
}
