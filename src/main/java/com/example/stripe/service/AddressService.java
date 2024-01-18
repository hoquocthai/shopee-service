package com.example.stripe.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.example.stripe.service.dto.request.AddressRequestDto;
import com.example.stripe.service.dto.response.AddressResponseDto;

import lombok.NonNull;

public interface AddressService {

  AddressResponseDto createAddress(AddressRequestDto addressRequestDto, HttpServletRequest httpServletRequest);

  List<AddressResponseDto> getAddresses(HttpServletRequest httpServletRequest);

  void updateAddress(Long addressId, AddressRequestDto addressRequestDto);

  AddressResponseDto getAddressById(Long addressId);

}
