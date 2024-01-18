package com.example.stripe.controller.publics;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.stripe.service.ProductService;
import com.example.stripe.service.dto.request.ProductRequestDto;
import com.example.stripe.service.dto.response.ProductResponseDto;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @ApiOperation(value = "API to create product")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @PostMapping(produces = "application/vn.shopee.api-v1+json")
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto dto) {
        return ResponseEntity.ok(productService.createProduct(dto));
    }

    @ApiOperation(value = "API to get all product")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @GetMapping(produces = "application/vn.shopee.api-v1+json")
    public ResponseEntity<List<ProductResponseDto>> getAllProduct() {
        return ResponseEntity.ok(productService.getAllProduct());
    }

    @ApiOperation(value = "API to get all private product")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @GetMapping(value = "/private", produces = "application/vn.shopee.api-v1+json")
    public ResponseEntity<List<ProductResponseDto>> getAllPrivateProduct(
            @NonNull HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(productService.getAllPrivateProduct(httpServletRequest));
    }

    @ApiOperation(value = "API to get product")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @GetMapping(value = "/{id}", produces = "application/vn.shopee.api-v1+json")
    public ResponseEntity<ProductResponseDto> getProduct(
            @PathVariable Long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @ApiOperation(value = "API to update product")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @PutMapping(value = "/{productId}", produces = "application/vn.shopee.api-v1+json")
    public ResponseEntity<Void> updateProduct(@PathVariable Long productId, @RequestBody ProductRequestDto dto) {
        productService.updateProduct(productId, dto);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "API to update product")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @DeleteMapping(value = "/{productId}", produces = "application/vn.shopee.api-v1+json")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
