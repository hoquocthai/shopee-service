package com.example.stripe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.stripe.entity.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

}
