package com.example.armaria.core.usecases.equipament;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.armaria.core.domain.Equipament;
import com.example.armaria.errors.EquipamentNotFoundException;
import com.example.armaria.repositories.EquipamentRepository;
import com.example.armaria.repositories.StockItemRepository;

@Service
public class FindEquipamentBySerialNumberUseCase {
  private final EquipamentRepository equipamentRepository;

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
