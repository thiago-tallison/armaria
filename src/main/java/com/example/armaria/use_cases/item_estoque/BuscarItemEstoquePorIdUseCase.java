package com.example.armaria.use_cases.item_estoque;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.armaria.entities.ItemEstoque;
import com.example.armaria.errors.ItemEstoqueNaoEncontradoException;
import com.example.armaria.repositories.ItemEstoqueRepository;

@Service
public class BuscarItemEstoquePorIdUseCase {
  private final ItemEstoqueRepository itemEstoqueRepository;

  public BuscarItemEstoquePorIdUseCase(ItemEstoqueRepository itemEstoqueRepository) {
    this.itemEstoqueRepository = itemEstoqueRepository;
  }

  public Optional<ItemEstoque> execute(long id) {
    Optional<ItemEstoque> itemEstoque = itemEstoqueRepository.findById(id);

    if (!itemEstoque.isPresent()) {
      throw new ItemEstoqueNaoEncontradoException();
    }

    return itemEstoque;
  }
}
