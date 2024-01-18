package com.example.stripe.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.example.stripe.service.dto.request.ProductRequestDto;
import com.example.stripe.service.dto.response.ProductResponseDto;

import lombok.NonNull;

public interface ProductService {

    ProductResponseDto createProduct(ProductRequestDto dto);

    List<ProductResponseDto> getAllProduct();

    Void updateProduct(Long productId, ProductRequestDto dto);

    List<ProductResponseDto> getAllPrivateProduct(@NonNull HttpServletRequest httpServletRequest);

    void deleteProduct(Long productId);

    ProductResponseDto getProduct(Long id);

}
