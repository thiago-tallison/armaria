package com.example.armaria.controllers.item_estoque;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  public BuscarItemEstoquePorIdController(BuscarItemEstoquePorIdUseCase buscarItemEstoquePorIdUseCase) {
    this.buscarItemEstoquePorIdUseCase = buscarItemEstoquePorIdUseCase;
  }

  @GetMapping("{id}")
  public Optional<ItemEstoque> handle(@PathVariable Long id) {
    Optional<ItemEstoque> itemEstoque = buscarItemEstoquePorIdUseCase.execute(id);
    return itemEstoque;
  }
}