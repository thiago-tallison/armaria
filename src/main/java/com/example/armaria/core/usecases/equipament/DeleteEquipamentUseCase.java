package com.example.armaria.core.usecases.equipament;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.armaria.core.domain.Equipament;
import com.example.armaria.core.domain.StockItem;
import com.example.armaria.repositories.EquipamentRepository;
import com.example.armaria.repositories.StockItemRepository;

@Service
public class DeleteEquipamentUseCase {
  private final EquipamentRepository equipamentRepository;
  private final StockItemRepository stockItemRepository;

  public DeleteEquipamentUseCase(
      EquipamentRepository equipamentRepository,
      StockItemRepository stockItemRepository) {
    this.equipamentRepository = equipamentRepository;
    this.stockItemRepository = stockItemRepository;
  }

  public void execute(String serialNumber) {
    // verificar se existe
    Optional<Equipament> optionalEquipament = equipamentRepository.findBySerialNumber(serialNumber);

    if (optionalEquipament.isPresent()) {
      Optional<StockItem> optionalStockItem = stockItemRepository.findByEquipament(optionalEquipament.get());
      StockItem itemIndisponivel = optionalStockItem.get();
      itemIndisponivel.setAvailable(false);
      stockItemRepository.save(itemIndisponivel);
    }
  }
}
