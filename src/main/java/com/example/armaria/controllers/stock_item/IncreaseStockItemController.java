package com.example.armaria.controllers.stock_item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.armaria.dtos.stock_item.IncreaseStockItemQuantityDTO;
import com.example.armaria.use_cases.stock_item.IncreaseStockItemQuantityUseCase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/stock_items")
public class IncreaseStockItemController {
  private final IncreaseStockItemQuantityUseCase increaseStockItemQuantityUseCase;

  @Autowired
  public IncreaseStockItemController(IncreaseStockItemQuantityUseCase increaseStockItemQuantityUseCase) {
    this.increaseStockItemQuantityUseCase = increaseStockItemQuantityUseCase;
  }

  @PostMapping("/{id}/increase_quantity")
  public ResponseEntity<Void> handle(@PathVariable Long id,
      @Valid @RequestBody IncreaseStockItemQuantityDTO body) {
    increaseStockItemQuantityUseCase.execute(id, body.quantity());
    return ResponseEntity.ok().build();
  }

}
