package com.example.stripe.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfoDto {
    private String name;
    private String description;
    private boolean active;
    private String[] images;
    private DefaultPriceDataDto defaultPriceData;
}
