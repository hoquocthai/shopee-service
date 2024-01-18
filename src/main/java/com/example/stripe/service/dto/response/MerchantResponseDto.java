package com.example.stripe.service.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MerchantResponseDto {
    private Long id;
    private String businessName;
    private String businessIndustry;
    private String mccCode;
    private String businessPlace;
    private String serviceDescription;
    private String storeType;
    private String websiteUrl;
    private String storeAddress;
    private String storePhoto;
    private String annualPayment;
    private String monthlyPayment;
    private BigDecimal ortherAmount;
    private String monthlyTxnNumber;
    private String averageDeliveryTime;
    // private Long userId;
}
