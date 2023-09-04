package com.example.armaria.use_cases.equipamento;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.armaria.entities.Equipamento;
import com.example.armaria.entities.ItemEstoque;
import com.example.armaria.repositories.EquipamentoRepository;
import com.example.armaria.repositories.ItemEstoqueRepository;

@Service
public class DeletarEquipamentoUseCase {
  private final EquipamentoRepository equipamentoRepository;
  private final ItemEstoqueRepository itemEstoqueRepository;

  @Autowired
  public DeletarEquipamentoUseCase(
      EquipamentoRepository equipamentoRepository,
      ItemEstoqueRepository itemEstoqueRepository) {
    this.equipamentoRepository = equipamentoRepository;
    this.itemEstoqueRepository = itemEstoqueRepository;
  }

  public void execute(String numSerie) {
    // verificar se existe
    Optional<Equipamento> equipamento = equipamentoRepository.findByNumSerie(numSerie);

    if (equipamento.isPresent()) {
      Optional<ItemEstoque> item = itemEstoqueRepository.findByEquipamento(equipamento.get());
      ItemEstoque itemIndisponivel = item.get();
      itemIndisponivel.setDisponivel(false);
      itemEstoqueRepository.save(itemIndisponivel);
    }
  }
}
