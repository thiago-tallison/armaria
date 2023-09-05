package com.example.armaria.use_cases.item_estoque;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.armaria.entities.ItemEstoque;
import com.example.armaria.errors.ItemEstoqueNaoEncontradoException;
import com.example.armaria.repositories.ItemEstoqueRepository;

import jakarta.transaction.Transactional;

@Service
public class DiminuirQuantidadeEmEstoqueUseCase {
  private final ItemEstoqueRepository itemEstoqueRepository;

  public DiminuirQuantidadeEmEstoqueUseCase(ItemEstoqueRepository itemEstoqueRepository) {
    this.itemEstoqueRepository = itemEstoqueRepository;
  }

  @Transactional
  public void execute(Long id, int quantidade) {
    Optional<ItemEstoque> itemEstoque = itemEstoqueRepository.findById(id);

    if (!itemEstoque.isPresent()) {
      throw new ItemEstoqueNaoEncontradoException();
    }

    ItemEstoque item = itemEstoque.get();

    item.diminuirQuantidade(quantidade);
    itemEstoqueRepository.save(item);
  }

}
