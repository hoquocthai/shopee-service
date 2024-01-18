package com.example.stripe.service;

import com.example.stripe.service.dto.request.WebhookEventRequestDto;

public interface WebhookService {

    void handleWebhookRequest(WebhookEventRequestDto request);

    void createWebhook();

}
