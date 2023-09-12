package com.example.armaria.use_cases.stock_item;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.armaria.entities.Equipament;
import com.example.armaria.entities.ItemEstoque;
import com.example.armaria.errors.EquipamentNotFoundException;
import com.example.armaria.repositories.EquipamentRepository;
import com.example.armaria.repositories.ItemEstoqueRepository;

@Service
public class FindStockItemByEquipamentUseCase {
  private final ItemEstoqueRepository itemEstoqueRepository;
  private final EquipamentRepository equipamentRepository;

  public FindStockItemByEquipamentUseCase(
      ItemEstoqueRepository itemEstoqueRepository,
      EquipamentRepository equipamentRepository) {
    this.itemEstoqueRepository = itemEstoqueRepository;
    this.equipamentRepository = equipamentRepository;
  }

  public Optional<ItemEstoque> execute(String serialNumber) {
    Optional<Equipament> optionalEquipament = equipamentRepository
        .findBySerialNumber(serialNumber);

    if (!optionalEquipament.isPresent() || serialNumber == null) {
      throw new EquipamentNotFoundException(serialNumber);
    }

    Optional<ItemEstoque> itOptional = itemEstoqueRepository
        .findByEquipament(optionalEquipament.get());

    return itOptional;
  }

}
