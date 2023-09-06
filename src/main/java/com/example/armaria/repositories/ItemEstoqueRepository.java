package com.example.armaria.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.armaria.entities.Equipamento;
import com.example.armaria.entities.ItemEstoque;

public interface ItemEstoqueRepository extends JpaRepository<ItemEstoque, Long> {
    Optional<ItemEstoque> findByEquipamento(Equipamento equipamento);

    @Transactional
    @Modifying
    @Query("update ItemEstoque set quantidadeEmEstoque = quantidadeEmEstoque - :quantidade where id = :id and quantidadeEmEstoque >= :quantidade and :quantidade > 0")
    void diminuirQuantidadeEmEstoque(@Param("id") Long id, @Param("quantidade") Integer quantidade);

    @Transactional
    @Modifying
    @Query("update ItemEstoque set quantidadeEmEstoque = quantidadeEmEstoque + :quantidade where id = :id and quantidadeEmEstoque >= :quantidade and :quantidade > 0")
    void aumentarQuantidadeEmEstoque(@Param("id") Long id, @Param("quantidade") Integer quantidade);
}
