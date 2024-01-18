package com.example.stripe.controller.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.stripe.service.WebhookService;
import com.example.stripe.service.dto.request.WebhookEventRequestDto;

import lombok.RequiredArgsConstructor;

@RequestMapping("/webhook")
@RestController
@RequiredArgsConstructor
public class WebhookController {

  private final WebhookService webhookService;

  @PostMapping
  public ResponseEntity<Void> receiveEvent(@RequestBody WebhookEventRequestDto request) {
    webhookService.handleWebhookRequest(request);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public ResponseEntity<Boolean> createWebhook() {
    webhookService.createWebhook();
    return ResponseEntity.noContent().build();
  }
}
