package com.example.armaria.use_cases.equipament;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.armaria.entities.Equipament;
import com.example.armaria.entities.StockItem;
import com.example.armaria.errors.EquipamentAlreadyExistsException;
import com.example.armaria.repositories.EquipamentRepository;
import com.example.armaria.repositories.StockItemRepository;

import jakarta.transaction.Transactional;

@Service
public class CreateEquipamentUseCase {
  private final EquipamentRepository equipamentRepository;
  private final StockItemRepository stockItemRepository;

  @Autowired
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
