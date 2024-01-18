package com.example.stripe.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.stripe.entity.Merchant;
import com.example.stripe.entity.Product;
import com.example.stripe.entity.UserSession;
import com.example.stripe.exception.MerchantException;
import com.example.stripe.exception.ProductException;
import com.example.stripe.repository.MerchantRepository;
import com.example.stripe.repository.ProductRepository;
import com.example.stripe.repository.UserSessionRepository;
import com.example.stripe.service.ProductService;
import com.example.stripe.service.dto.request.ProductRequestDto;
import com.example.stripe.service.dto.response.ProductResponseDto;
import com.example.stripe.service.mapper.ProductMapper;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final UserSessionRepository sessionRepository;
    private final MerchantRepository merchantRepository;

    @Override
    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto dto) {
        return productMapper.toDto(productRepository.save(productMapper.toEntity(dto)));
    }

    @Override
    public List<ProductResponseDto> getAllProduct() {
        List<Product> products = productRepository.findAll();
        List<ProductResponseDto> dtos = products.stream().map(e -> productMapper.toDto(e)).collect(Collectors.toList());
        return dtos;
    }

    @Override
    @Transactional
    public Void updateProduct(Long productId, ProductRequestDto dto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException("product is not found"));
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setImage(dto.getImage());
        product.setPrice(dto.getPrice());
        product.setCategory(dto.getCategory());
        return null;
    }

    @Override
    public List<ProductResponseDto> getAllPrivateProduct(@NonNull HttpServletRequest httpServletRequest) {
        String authorizationToken = httpServletRequest.getHeader("Authorization").substring(7);
        UserSession userSession = sessionRepository.findByToken(authorizationToken).get();
        Merchant merchant = merchantRepository.findByUser(userSession.getUser())
                .orElseThrow(() -> new MerchantException("Merchant is not found"));

        List<Product> products = productRepository.findAll();
        return products.stream().filter((e) -> e.getMerchant().getId() == merchant.getId())
                .map(e -> productMapper.toDto(e))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public ProductResponseDto getProduct(Long id) {
        return productMapper
                .toDto(productRepository.findById(id).orElseThrow(() -> new ProductException("Product is not found")));
    }
}
