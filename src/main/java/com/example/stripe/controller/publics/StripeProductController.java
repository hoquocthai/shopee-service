package com.example.stripe.controller.publics;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.stripe.service.StripeProductService;
import com.example.stripe.service.dto.ProductInfoDto;
import com.example.stripe.service.dto.request.PriceRequestDto;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class StripeProductController {

    private final StripeProductService service;

    @ApiOperation(value = "API to create stripe product")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @PostMapping(produces = "application/vn.shopee.api-v1+json")
    public ResponseEntity<String> createProduct(@RequestBody ProductInfoDto dto) {
        return ResponseEntity.ok(service.createProduct(dto));
    }

    @ApiOperation(value = "API to update stripe product")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @PutMapping(value = "/{id}", produces = "application/vn.shopee.api-v1+json")
    public ResponseEntity<Void> updateProduct(@PathVariable String id, @RequestBody ProductInfoDto dto) {
        service.updateProduct(id, dto);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "API to enable stripe product")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @GetMapping(value = "/enable/{id}", produces = "application/vn.shopee.api-v1+json")
    public ResponseEntity<Void> enableProduct(@PathVariable String id) {
        service.enableProduct(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "API to disable stripe product")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @GetMapping(value = "/disable/{id}", produces = "application/vn.shopee.api-v1+json")
    public ResponseEntity<Void> disableProduct(@PathVariable String id) {
        service.disableProduct(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "API to create stripe price")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @PostMapping(value = "/price", produces = "application/vn.shopee.api-v1+json")
    public ResponseEntity<String> createPrice(@RequestBody PriceRequestDto dto) {
        return ResponseEntity.ok(service.createPrice(dto));
    }

    @ApiOperation(value = "API to update stripe price")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @PutMapping(value = "/price/{id}", produces = "application/vn.shopee.api-v1+json")
    public ResponseEntity<Void> updatePrice(@PathVariable String id, @RequestBody PriceRequestDto dto) {
        service.updatePrice(id, dto);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "API to enable stripe price")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @GetMapping(value = "/price/enable/{id}", produces = "application/vn.shopee.api-v1+json")
    public ResponseEntity<Void> enablePrice(@PathVariable String id) {
        service.enablePrice(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "API to disable stripe price")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @GetMapping(value = "/price/disable/{id}", produces = "application/vn.shopee.api-v1+json")
    public ResponseEntity<Void> disablePrice(@PathVariable String id) {
        service.disablePrice(id);
        return ResponseEntity.noContent().build();
    }
}
