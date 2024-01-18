package com.example.stripe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.stripe.entity.Address;
import com.example.stripe.entity.User;

public interface AddressRepository extends JpaRepository<Address, Long> {

  List<Address> findAllByUserId(Long userId);
  // List<Address> findAllByUser(User user);
}
