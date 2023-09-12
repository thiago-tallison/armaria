package com.example.armaria.use_cases.stock_item;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.armaria.core.domain.StockItem;
import com.example.armaria.errors.ItemEstoqueNaoEncontradoException;
import com.example.armaria.repositories.StockItemRepository;

import jakarta.transaction.Transactional;

@Service
public class IncreaseStockItemQuantityUseCase {
  private final StockItemRepository stockItemRepository;

  public IncreaseStockItemQuantityUseCase(StockItemRepository stockItemRepository) {
    this.stockItemRepository = stockItemRepository;
  }

  @Transactional
  public void execute(Long id, int quantity) {
    Optional<StockItem> optionalStockItem = stockItemRepository.findById(id);

    if (!optionalStockItem.isPresent()) {
      throw new ItemEstoqueNaoEncontradoException();
    }

    StockItem item = optionalStockItem.get();

    item.aumentarQuantidade(quantity);
    stockItemRepository.save(item);
  }

}
