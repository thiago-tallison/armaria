package com.example.armaria.use_cases.item_estoque;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.armaria.entities.ItemEstoque;
import com.example.armaria.errors.ItemEstoqueNaoEncontradoException;
import com.example.armaria.repositories.ItemEstoqueRepository;

import jakarta.transaction.Transactional;

@Service
public class DeletarItemEstoqueUseCase {
  private final ItemEstoqueRepository itemEstoqueRepository;

  @Autowired
  public DeletarItemEstoqueUseCase(
      ItemEstoqueRepository itemEstoqueRepository) {
    this.itemEstoqueRepository = itemEstoqueRepository;
  }

  @Transactional
  public void execute(Long id) {
    Optional<ItemEstoque> itemEstoque = itemEstoqueRepository.findById(id);

    if (!itemEstoque.isPresent()) {
      throw new ItemEstoqueNaoEncontradoException();
    }

    ItemEstoque item = itemEstoque.get();
    item.setDisponivel(false);
    itemEstoqueRepository.save(item);
  }
}
