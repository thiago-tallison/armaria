package com.example.armaria.controllers.stock_item;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.armaria.entities.ItemEstoque;
import com.example.armaria.use_cases.stock_item.FindStockItemByEquipamentUseCase;

@RestController
@RequestMapping("/api/v1/stock_items")
public class FindStockItemByEquipamentController {
  private final FindStockItemByEquipamentUseCase findStockItemByEquipamentUseCase;

  public FindStockItemByEquipamentController(
      FindStockItemByEquipamentUseCase findStockItemByEquipamentUseCase) {
    this.findStockItemByEquipamentUseCase = findStockItemByEquipamentUseCase;
  }

  @GetMapping("/serial_number/{serialNumber}")
  public ResponseEntity<Optional<ItemEstoque>> handle(@PathVariable String serialNumber) {
    Optional<ItemEstoque> itemEstoque = findStockItemByEquipamentUseCase.execute(serialNumber);

    return ResponseEntity.ok().body(itemEstoque);
  }
}
