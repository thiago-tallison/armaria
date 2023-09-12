package com.example.armaria.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.armaria.entities.Checkout;

public interface CheckoutRepository extends JpaRepository<Checkout, Long> {

}
