package com.example.stripe.service.mapper;

import org.springframework.stereotype.Service;

import com.example.stripe.entity.Merchant;
import com.example.stripe.entity.Product;
import com.example.stripe.exception.MerchantException;
import com.example.stripe.repository.MerchantRepository;
import com.example.stripe.service.dto.request.ProductRequestDto;
import com.example.stripe.service.dto.response.ProductResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductMapper {

    private final MerchantRepository merchantRepository;

    public ProductResponseDto toDto(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .image(product.getImage())
                .price(product.getPrice())
                .merchantId(product.getMerchant().getId())
                .category(product.getCategory())
                .build();
    }

    public Product toEntity(ProductRequestDto dto) {
        Merchant merchant = merchantRepository.findById(dto.getMerchantId())
                .orElseThrow(() -> new MerchantException("Merchant is not found"));

        Product product = new Product();
        product.setMerchant(merchant);
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setImage(dto.getImage());
        product.setPrice(dto.getPrice());
        product.setCategory(dto.getCategory());

        return product;
    }
}
