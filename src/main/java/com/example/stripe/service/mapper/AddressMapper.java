package com.example.stripe.service.mapper;

import org.springframework.stereotype.Service;

import com.example.stripe.entity.Address;
import com.example.stripe.entity.User;
import com.example.stripe.exception.AddressException;
import com.example.stripe.repository.UserRepository;
import com.example.stripe.service.dto.request.AddressRequestDto;
import com.example.stripe.service.dto.response.AddressResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressMapper {

  public AddressResponseDto toDto(Address address) {
    return AddressResponseDto.builder()
        .id(address.getId())
        .userName(address.getUserName())
        .phoneNumber(address.getPhoneNumber())
        .address(address.getAddress())
        .detailAddress(address.getDetailAddress())
        .userId(address.getUser().getId())
        .build();
  }

  public Address toEntity(AddressRequestDto dto, User user) {
    return Address.builder()
        .userName(dto.getUserName())
        .phoneNumber(dto.getPhoneNumber())
        .address(dto.getAddress())
        .detailAddress(dto.getDetailAddress())
        .user(user)
        .build();

  }
}
