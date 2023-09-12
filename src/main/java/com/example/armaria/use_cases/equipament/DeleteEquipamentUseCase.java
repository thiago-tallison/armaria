package com.example.armaria.use_cases.equipament;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.armaria.entities.Equipament;
import com.example.armaria.entities.ItemEstoque;
import com.example.armaria.repositories.EquipamentRepository;
import com.example.armaria.repositories.ItemEstoqueRepository;

@Service
public class DeleteEquipamentUseCase {
  private final EquipamentRepository equipamentRepository;
  private final ItemEstoqueRepository itemEstoqueRepository;

  @Autowired
  public DeleteEquipamentUseCase(
      EquipamentRepository equipamentRepository,
      ItemEstoqueRepository itemEstoqueRepository) {
    this.equipamentRepository = equipamentRepository;
    this.itemEstoqueRepository = itemEstoqueRepository;
  }

  public void execute(String serialNumber) {
    // verificar se existe
    Optional<Equipament> optionalEquipament = equipamentRepository.findBySerialNumber(serialNumber);

    if (optionalEquipament.isPresent()) {
      Optional<ItemEstoque> item = itemEstoqueRepository.findByEquipament(optionalEquipament.get());
      ItemEstoque itemIndisponivel = item.get();
      itemIndisponivel.setDisponivel(false);
      itemEstoqueRepository.save(itemIndisponivel);
    }
  }
}
