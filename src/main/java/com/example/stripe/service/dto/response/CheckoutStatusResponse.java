package com.example.stripe.service.dto.response;
import com.example.stripe.enums.CheckoutStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutStatusResponse {
  private CheckoutStatus status;
}
