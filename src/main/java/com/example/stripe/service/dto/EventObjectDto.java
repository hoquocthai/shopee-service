package com.example.stripe.service.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventObjectDto {
  private String id;
  private String object;
  private BigDecimal amount_subtotal;
  private BigDecimal amount_total;
  private BigDecimal amount;
  private String currency;
  private String payment_status;
  private String status;
  private String payment_intent;
}
