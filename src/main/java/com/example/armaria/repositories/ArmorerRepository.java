package com.example.armaria.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.armaria.entities.Armorer;

public interface ArmorerRepository extends JpaRepository<Armorer, String> {
  Optional<Armorer> findByRegistrationNumber(String registration);
}
