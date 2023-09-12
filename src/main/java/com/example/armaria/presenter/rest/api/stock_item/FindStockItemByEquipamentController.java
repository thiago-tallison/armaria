package com.example.armaria.presenter.rest.api.stock_item;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.armaria.core.domain.StockItem;
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
  public ResponseEntity<Optional<StockItem>> handle(@PathVariable String serialNumber) {
    Optional<StockItem> optionalStockItem = findStockItemByEquipamentUseCase.execute(serialNumber);

    return ResponseEntity.ok().body(optionalStockItem);
  }
}
