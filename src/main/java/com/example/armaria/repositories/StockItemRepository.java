package com.example.armaria.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.armaria.entities.Equipament;
import com.example.armaria.entities.StockItem;

public interface StockItemRepository extends JpaRepository<StockItem, Long> {
    Optional<StockItem> findByEquipament(Equipament equipament);

    @Transactional
    @Modifying
    @Query("update StockItem set quantityInStock = quantityInStock - :quantity where id = :id and quantityInStock >= :quantity and :quantity > 0")
    int increaseStockItemQuantity(@Param("id") Long id, @Param("quantity") Integer quantity);

    @Transactional
    @Modifying
    @Query("update StockItem set quantityInStock = quantityInStock + :quantity where id = :id and quantityInStock >= :quantity and :quantity > 0")
    int decreaseStockItemQuantity(@Param("id") Long id, @Param("quantity") Integer quantity);
}
