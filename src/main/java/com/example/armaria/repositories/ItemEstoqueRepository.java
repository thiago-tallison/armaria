package com.example.armaria.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.armaria.entities.Equipamento;
import com.example.armaria.entities.ItemEstoque;

import jakarta.transaction.Transactional;

public interface ItemEstoqueRepository extends JpaRepository<ItemEstoque, Long> {
    Optional<ItemEstoque> findByEquipamento(Equipamento equipamento);

    @Query(value = """
            select * from itens_estoque
            where id = ?1
            and quantidade_em_estoque <= ?2;
            """)
    Optional<ItemEstoque> existeEQuantidadeDisponivelEmEstoque(Long id,
            Integer quantidadeASerAcautelada);

    @Transactional
    @Modifying
    @Query("""
            UPDATE itens_estoque
            SET quantidade_em_estoque = quantidade_em_estoque - :quantidade
            WHERE id = :itemId
            AND quantidade_em_estoque <= :quantidade
            """)
    void diminuirQuantidadeEmEstoque(@Param("itemId") Long itemId,
            @Param("quantidade") Integer quantidade);
}
