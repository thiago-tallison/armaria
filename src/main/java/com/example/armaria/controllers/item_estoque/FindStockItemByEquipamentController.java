package com.example.armaria.controllers.item_estoque;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.armaria.entities.ItemEstoque;
import com.example.armaria.use_cases.item_estoque.FindStockItemByEquipamentUseCase;

@RestController
@RequestMapping("/api/item-estoque")
public class FindStockItemByEquipamentController {
  private final FindStockItemByEquipamentUseCase findStockItemByEquipamentUseCase;

  public FindStockItemByEquipamentController(
      FindStockItemByEquipamentUseCase findStockItemByEquipamentUseCase) {
    this.findStockItemByEquipamentUseCase = findStockItemByEquipamentUseCase;
  }

  @GetMapping("/num-serie/{numSerie}")
  public ResponseEntity<Optional<ItemEstoque>> handle(@PathVariable String numSerie) {
    Optional<ItemEstoque> itemEstoque = findStockItemByEquipamentUseCase.execute(numSerie);

    return ResponseEntity.ok().body(itemEstoque);
  }
}
