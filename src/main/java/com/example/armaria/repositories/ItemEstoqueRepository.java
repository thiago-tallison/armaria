package com.example.armaria.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.armaria.entities.Equipament;
import com.example.armaria.entities.ItemEstoque;

public interface ItemEstoqueRepository extends JpaRepository<ItemEstoque, Long> {
    Optional<ItemEstoque> findByEquipament(Equipament equipament);

    @Transactional
    @Modifying
    @Query("update ItemEstoque set quantityInStock = quantityInStock - :quantidade where id = :id and quantityInStock >= :quantidade and :quantidade > 0")
    int diminuirQuantidadeEmEstoque(@Param("id") Long id, @Param("quantidade") Integer quantidade);

    @Transactional
    @Modifying
    @Query("update ItemEstoque set quantityInStock = quantityInStock + :quantidade where id = :id and quantityInStock >= :quantidade and :quantidade > 0")
    int aumentarQuantidadeEmEstoque(@Param("id") Long id, @Param("quantidade") Integer quantidade);
}
