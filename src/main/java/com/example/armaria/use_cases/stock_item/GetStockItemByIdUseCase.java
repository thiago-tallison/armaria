package com.example.armaria.use_cases.stock_item;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.armaria.core.domain.StockItem;
import com.example.armaria.errors.ItemEstoqueNaoEncontradoException;
import com.example.armaria.repositories.StockItemRepository;

@Service
public class GetStockItemByIdUseCase {
  private final StockItemRepository stockItemRepository;

  public GetStockItemByIdUseCase(StockItemRepository stockItemRepository) {
    this.stockItemRepository = stockItemRepository;
  }

  public Optional<StockItem> execute(long id) {
    Optional<StockItem> optionalStockItem = stockItemRepository.findById(id);

    if (!optionalStockItem.isPresent()) {
      throw new ItemEstoqueNaoEncontradoException();
    }

    return optionalStockItem;
  }
}
