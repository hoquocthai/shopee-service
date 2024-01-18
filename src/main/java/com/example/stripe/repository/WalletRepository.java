package com.example.stripe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.stripe.entity.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    boolean existsByUserId(Long userId);

    Optional<Wallet> findByUserId(Long userId);

    Optional<Wallet> findByUserIdAndCoinShortName(Long userId, String coinName);
}
