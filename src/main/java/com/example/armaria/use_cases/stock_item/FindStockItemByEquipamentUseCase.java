package com.example.armaria.use_cases.stock_item;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.armaria.core.domain.Equipament;
import com.example.armaria.core.domain.StockItem;
import com.example.armaria.errors.EquipamentNotFoundException;
import com.example.armaria.repositories.EquipamentRepository;
import com.example.armaria.repositories.StockItemRepository;

@Service
public class FindStockItemByEquipamentUseCase {
  private final StockItemRepository stockItemRepository;
  private final EquipamentRepository equipamentRepository;

  public FindStockItemByEquipamentUseCase(
      StockItemRepository stockItemRepository,
      EquipamentRepository equipamentRepository) {
    this.stockItemRepository = stockItemRepository;
    this.equipamentRepository = equipamentRepository;
  }

  public Optional<StockItem> execute(String serialNumber) {
    Optional<Equipament> optionalEquipament = equipamentRepository
        .findBySerialNumber(serialNumber);

    if (!optionalEquipament.isPresent() || serialNumber == null) {
      throw new EquipamentNotFoundException(serialNumber);
    }

    Optional<StockItem> optionalStockItem = stockItemRepository
        .findByEquipament(optionalEquipament.get());

    return optionalStockItem;
  }

}
