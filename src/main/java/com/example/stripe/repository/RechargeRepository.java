package com.example.stripe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.stripe.entity.RechargeHistory;

@Repository
public interface RechargeRepository extends JpaRepository<RechargeHistory, Long> {

}
