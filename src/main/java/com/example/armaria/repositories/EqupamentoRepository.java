package com.example.armaria.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.armaria.entities.Equipamento;

public interface EqupamentoRepository extends JpaRepository<Equipamento, Long> {
  Optional<Equipamento> findByNumSerie(String numSerie);
}
