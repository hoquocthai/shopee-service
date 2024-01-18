package com.example.stripe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.stripe.entity.SessionRecord;

@Repository
public interface SessionRecordRepository extends JpaRepository<SessionRecord, Long> {
  Optional<SessionRecord> findByCheckoutSessionIdOrPaymentIntentId(String checkoutSessionId, String paymentIntentId);

  Optional<SessionRecord> findByPaymentIntentId(String paymentIntentId);
}
