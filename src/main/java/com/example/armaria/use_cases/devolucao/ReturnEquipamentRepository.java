package com.example.armaria.use_cases.devolucao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.armaria.entities.Devolucao;

public interface ReturnEquipamentRepository extends JpaRepository<Devolucao, Long> {

}