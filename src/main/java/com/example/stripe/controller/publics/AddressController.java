package com.example.stripe.controller.publics;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.stripe.service.AddressService;
import com.example.stripe.service.dto.request.AddressRequestDto;
import com.example.stripe.service.dto.response.AddressResponseDto;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressController {

  private final AddressService addressService;

  @ApiOperation(value = "API to create address")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
      @ApiResponse(code = 500, message = "Internal server error") })
  @PostMapping(produces = "application/vn.shopee.api-v1+json")
  public ResponseEntity<AddressResponseDto> createAddress(@RequestBody AddressRequestDto addressRequestDto,
      @NonNull HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(addressService.createAddress(addressRequestDto, httpServletRequest));
  }

  @ApiOperation(value = "API to get all address by user id")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
      @ApiResponse(code = 500, message = "Internal server error") })
  @GetMapping(produces = "application/vn.shopee.api-v1+json")
  public ResponseEntity<List<AddressResponseDto>> getAddresses(@NonNull HttpServletRequest httpServletRequest) {
    return ResponseEntity.ok(addressService.getAddresses(httpServletRequest));
  }

  @ApiOperation(value = "API to update address")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
      @ApiResponse(code = 500, message = "Internal server error") })
  @PutMapping(value = "/{addressId}", produces = "application/vn.shopee.api-v1+json")
  public ResponseEntity<Void> updateAddress(@PathVariable Long addressId,
      @RequestBody AddressRequestDto addressRequestDto) {
    addressService.updateAddress(addressId, addressRequestDto);
    return ResponseEntity.noContent().build();
  }

  @ApiOperation(value = "API to get address by address ID")
  @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully"),
      @ApiResponse(code = 500, message = "Internal server error") })
  @GetMapping(value = "/{addressId}", produces = "application/vn.shopee.api-v1+json")
  public ResponseEntity<AddressResponseDto> getAddress(@PathVariable Long addressId) {
    return ResponseEntity.ok(addressService.getAddressById(addressId));
  }
}
