package com.example.armaria.core.usecases.equipament;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.armaria.core.domain.Equipament;
import com.example.armaria.core.domain.StockItem;
import com.example.armaria.errors.EquipamentAlreadyExistsException;
import com.example.armaria.repositories.EquipamentRepository;
import com.example.armaria.repositories.StockItemRepository;

import jakarta.transaction.Transactional;

@Service
public class CreateEquipamentUseCase {
  private final EquipamentRepository equipamentRepository;
  private final StockItemRepository stockItemRepository;

  public CreateEquipamentUseCase(EquipamentRepository equipamentRepository,
      StockItemRepository stockItemRepository) {
    this.equipamentRepository = equipamentRepository;
    this.stockItemRepository = stockItemRepository;
  }

  @Transactional
  public void execute(EquipamentCreateDTO equipamentCreateDTO) {
    Optional<Equipament> optionalEquipament = equipamentRepository
        .findBySerialNumber(equipamentCreateDTO.serialNumber());

    if (optionalEquipament.isPresent()) {
      throw new EquipamentAlreadyExistsException(equipamentCreateDTO.serialNumber());
    }

    Equipament equipament = new Equipament(equipamentCreateDTO);
    equipamentRepository.save(equipament);

    StockItem stockItem = new StockItem(equipament,
        equipamentCreateDTO.quantityInStock());
    stockItemRepository.save(stockItem);
  }
}
