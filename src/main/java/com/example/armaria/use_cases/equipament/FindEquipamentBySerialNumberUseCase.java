package com.example.armaria.use_cases.equipament;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.armaria.entities.Equipament;
import com.example.armaria.errors.EquipamentNotFoundException;
import com.example.armaria.repositories.EquipamentRepository;
import com.example.armaria.repositories.StockItemRepository;

@Service
public class FindEquipamentBySerialNumberUseCase {
  private final EquipamentRepository equipamentRepository;

  @Autowired
  public FindEquipamentBySerialNumberUseCase(EquipamentRepository equipamentRepository,
      StockItemRepository stockItemRepository) {
    this.equipamentRepository = equipamentRepository;
  }

  public Optional<Equipament> execute(String serialNumber) {
    Optional<Equipament> optionalEquipament = equipamentRepository.findBySerialNumber(serialNumber);

    if (!optionalEquipament.isPresent()) {
      throw new EquipamentNotFoundException();
    }

    return optionalEquipament;
  }

}
