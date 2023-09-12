package com.example.armaria.presenter.rest.api.stock_item;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.armaria.core.domain.StockItem;
import com.example.armaria.use_cases.stock_item.GetStockItemByIdUseCase;

@RestController
@RequestMapping("/api/v1/stock_items")
public class BuscarItemEstoquePorIdController {
  private final GetStockItemByIdUseCase buscarItemEstoquePorIdUseCase;

  public BuscarItemEstoquePorIdController(GetStockItemByIdUseCase buscarItemEstoquePorIdUseCase) {
    this.buscarItemEstoquePorIdUseCase = buscarItemEstoquePorIdUseCase;
  }

  @GetMapping("{id}")
  public ResponseEntity<Optional<StockItem>> handle(@PathVariable Long id) {
    Optional<StockItem> optionalStockItem = buscarItemEstoquePorIdUseCase.execute(id);
    return ResponseEntity.ok().body(optionalStockItem);
  }
}