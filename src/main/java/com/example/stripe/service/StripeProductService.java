package com.example.stripe.service;

import com.example.stripe.service.dto.ProductInfoDto;
import com.example.stripe.service.dto.request.PriceRequestDto;

public interface StripeProductService {
    String createProduct(ProductInfoDto dto);

    void updateProduct(String id, ProductInfoDto dto);

    String createPrice(PriceRequestDto dto);

    void updatePrice(String id, PriceRequestDto dto);

    void enableProduct(String id);

    void disableProduct(String id);

    void enablePrice(String id);

    void disablePrice(String id);
}
