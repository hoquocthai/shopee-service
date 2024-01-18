package com.example.stripe.service.dto.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantRequestDto {
    @NotBlank(message = "businessName is required")
    private String businessName;

    @NotBlank(message = "businessIndustry is required")
    private String businessIndustry;

    @NotBlank(message = "mccCode is required")
    private String mccCode;

    @NotBlank(message = "businessPlace is required")
    private String businessPlace;

    @NotBlank(message = "serviceDescription is required")
    private String serviceDescription;

    @NotBlank(message = "storeType is required")
    private String storeType;

    @NotBlank(message = "websiteUrl is required")
    private String websiteUrl;

    @NotBlank(message = "storeAddress is required")
    private String storeAddress;

    @NotBlank(message = "storePhoto is required")
    private String storePhoto;

    @NotBlank(message = "annualPayment is required")
    private String annualPayment;

    @NotBlank(message = "monthlyPayment is required")
    private String monthlyPayment;

    @NotNull(message = "ortherAmount is required")
    private BigDecimal ortherAmount;

    @NotBlank(message = "monthlyTxnNumber is required")
    private String monthlyTxnNumber;

    @NotBlank(message = "averageDeliveryTime is required")
    private String averageDeliveryTime;
}
