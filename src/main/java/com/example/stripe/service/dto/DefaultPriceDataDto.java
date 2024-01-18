package com.example.stripe.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DefaultPriceDataDto {
    private String currency;
    private Long unitAmount;
}
