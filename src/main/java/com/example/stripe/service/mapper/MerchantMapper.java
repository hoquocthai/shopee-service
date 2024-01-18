package com.example.stripe.service.mapper;

import org.springframework.stereotype.Service;

import com.example.stripe.entity.Merchant;
import com.example.stripe.entity.User;
import com.example.stripe.service.dto.request.MerchantRequestDto;
import com.example.stripe.service.dto.response.MerchantResponseDto;

@Service
public class MerchantMapper {
    public MerchantResponseDto toDto(Merchant merchant) {
        if (merchant == null)
            return null;
        return MerchantResponseDto
                .builder()
                .id(merchant.getId())
                .businessName(merchant.getBusinessName())
                .businessIndustry(merchant.getBusinessIndustry())
                .mccCode(merchant.getMccCode())
                .businessPlace(merchant.getBusinessPlace())
                .serviceDescription(merchant.getServiceDescription())
                .storeType(merchant.getStoreType())
                .websiteUrl(merchant.getWebsiteUrl())
                .storeAddress(merchant.getStoreAddress())
                .storePhoto(merchant.getStorePhoto())
                .annualPayment(merchant.getAnnualPayment())
                .monthlyPayment(merchant.getMonthlyPayment())
                .ortherAmount(merchant.getOrtherAmount())
                .monthlyTxnNumber(merchant.getMonthlyTxnNumber())
                .averageDeliveryTime(merchant.getAverageDeliveryTime())
                .build();
    }

    public Merchant toEntity(MerchantRequestDto dto, User user) {
        if (dto == null)
            return null;
        return Merchant
                .builder()
                .businessName(dto.getBusinessName())
                .businessIndustry(dto.getBusinessIndustry())
                .mccCode(dto.getMccCode())
                .businessPlace(dto.getBusinessPlace())
                .serviceDescription(dto.getServiceDescription())
                .storeType(dto.getStoreType())
                .websiteUrl(dto.getWebsiteUrl())
                .storeAddress(dto.getStoreAddress())
                .storePhoto(dto.getStorePhoto())
                .annualPayment(dto.getAnnualPayment())
                .monthlyPayment(dto.getMonthlyPayment())
                .ortherAmount(dto.getOrtherAmount())
                .monthlyTxnNumber(dto.getMonthlyTxnNumber())
                .averageDeliveryTime(dto.getAverageDeliveryTime())
                .user(user)
                .build();
    }
}
