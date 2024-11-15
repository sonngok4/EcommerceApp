package com.example.ecommerce_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce_app.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
