package com.vgnit.medical.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vgnit.medical.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("SELECT p FROM Payment p WHERE p.accountName LIKE %:keyword% OR p.paymentMethod LIKE %:keyword% OR p.status LIKE %:keyword%")
    List<Payment> findthis(@Param("keyword") String keyword);
}