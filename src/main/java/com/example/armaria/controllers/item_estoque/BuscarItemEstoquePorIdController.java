package com.example.armaria.controllers.item_estoque;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.armaria.entities.ItemEstoque;
import com.example.armaria.use_cases.item_estoque.BuscarItemEstoquePorIdUseCase;

@RestController
@RequestMapping("/api/item-estoque")
public class BuscarItemEstoquePorIdController {
  private final BuscarItemEstoquePorIdUseCase buscarItemEstoquePorIdUseCase;

  public BuscarItemEstoquePorIdController(BuscarItemEstoquePorIdUseCase buscarItemEstoquePorIdUseCase) {
    this.buscarItemEstoquePorIdUseCase = buscarItemEstoquePorIdUseCase;
  }

  @GetMapping("{id}")
  public ResponseEntity<Optional<ItemEstoque>> handle(@PathVariable Long id) {
    Optional<ItemEstoque> itemEstoque = buscarItemEstoquePorIdUseCase.execute(id);
    return ResponseEntity.ok().body(itemEstoque);
  }
}