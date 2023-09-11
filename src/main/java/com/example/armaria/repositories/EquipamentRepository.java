package com.example.armaria.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.armaria.entities.Equipament;

public interface EquipamentRepository
    extends JpaRepository<Equipament, Long> {
  Optional<Equipament> findBySerialNumber(String serialNumber);
}
