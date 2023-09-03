package com.example.armaria.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.armaria.entities.ItemEstoque;

public interface ItemEstoqueRepository extends JpaRepository<ItemEstoque, Long> {

}
