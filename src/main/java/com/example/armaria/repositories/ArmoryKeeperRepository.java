package com.example.armaria.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.armaria.entities.ArmoryKepper;

public interface ArmoryKeeperRepository extends JpaRepository<ArmoryKepper, String> {
  Optional<ArmoryKepper> findByRegistrationNumber(String registration);
}
