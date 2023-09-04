package com.example.armaria.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.armaria.entities.Equipamento;
import com.example.armaria.entities.ItemEstoque;

public interface ItemEstoqueRepository extends JpaRepository<ItemEstoque, Long> {

  Optional<ItemEstoque> findByEquipamento(Equipamento equipamento);

}
