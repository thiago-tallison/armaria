package com.example.armaria.use_cases.equipament;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.armaria.entities.Equipament;
import com.example.armaria.entities.ItemEstoque;
import com.example.armaria.errors.EquipamentAlreadyExistsException;
import com.example.armaria.repositories.EquipamentRepository;
import com.example.armaria.repositories.ItemEstoqueRepository;

import jakarta.transaction.Transactional;

@Service
public class CreateEquipamentUseCase {
  private final EquipamentRepository equipamentRepository;
  private final ItemEstoqueRepository itemEstoqueRepository;

  @Autowired
  public CreateEquipamentUseCase(EquipamentRepository equipamentRepository,
      ItemEstoqueRepository itemEstoqueRepository) {
    this.equipamentRepository = equipamentRepository;
    this.itemEstoqueRepository = itemEstoqueRepository;
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

    ItemEstoque itemEstoque = new ItemEstoque(equipament,
        equipamentCreateDTO.quantityInStock());
    itemEstoqueRepository.save(itemEstoque);
  }
}
