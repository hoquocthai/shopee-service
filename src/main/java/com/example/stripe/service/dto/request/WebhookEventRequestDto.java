package com.example.stripe.service.dto.request;

import com.example.stripe.service.dto.EventDataDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebhookEventRequestDto {
  private String id;
  private String type;
  private EventDataDto data;
}
