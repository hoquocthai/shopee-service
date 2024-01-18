package com.example.stripe.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseDto {
  private Long id;
  private String userName;
  private String phoneNumber;
  private String address;
  private String detailAddress;
  private Long userId;
}
