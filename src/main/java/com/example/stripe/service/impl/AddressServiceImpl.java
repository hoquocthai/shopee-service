package com.example.stripe.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.stripe.entity.Address;
import com.example.stripe.entity.UserSession;
import com.example.stripe.exception.AddressException;
import com.example.stripe.repository.AddressRepository;
import com.example.stripe.repository.UserSessionRepository;
import com.example.stripe.service.AddressService;
import com.example.stripe.service.dto.request.AddressRequestDto;
import com.example.stripe.service.dto.response.AddressResponseDto;
import com.example.stripe.service.mapper.AddressMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AddressServiceImpl implements AddressService {

  private final AddressRepository addressRepository;
  private final AddressMapper addressMapper;

  private final UserSessionRepository sessionRepository;

  @Override
  @Transactional
  public AddressResponseDto createAddress(AddressRequestDto addressRequestDto, HttpServletRequest httpServletRequest) {
    String authorizationToken = httpServletRequest.getHeader("Authorization").substring(7);
    UserSession userSession = sessionRepository.findByToken(authorizationToken).get();

    return addressMapper
        .toDto(addressRepository.save(addressMapper.toEntity(addressRequestDto, userSession.getUser())));
  }

  @Override
  public List<AddressResponseDto> getAddresses(HttpServletRequest httpServletRequest) {
    String authorizationToken = httpServletRequest.getHeader("Authorization").substring(7);
    UserSession userSession = sessionRepository.findByToken(authorizationToken).get();

    List<Address> addresses = addressRepository.findAllByUserId(userSession.getUser().getId());
    return addresses.stream().map(e -> addressMapper.toDto(e)).collect(Collectors.toList());
  }

  @Override
  @Transactional
  public void updateAddress(Long addressId, AddressRequestDto addressRequestDto) {
    Address address = addressRepository.findById(addressId)
        .orElseThrow(() -> new AddressException("Address is not found"));

    address.setAddress(addressRequestDto.getAddress());
    address.setDetailAddress(addressRequestDto.getDetailAddress());
    address.setUserName(addressRequestDto.getUserName());
    address.setPhoneNumber(addressRequestDto.getPhoneNumber());
  }

  @Override
  public AddressResponseDto getAddressById(Long addressId) {
    Address address = addressRepository.findById(addressId)
        .orElseThrow(() -> new AddressException("Address is not found"));

    return addressMapper.toDto(address);
  }
}
