package com.example.armaria.use_cases.equipamento;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.armaria.entities.Equipamento;
import com.example.armaria.entities.ItemEstoque;
import com.example.armaria.errors.EquipamentoExistenteException;
import com.example.armaria.repositories.EqupamentoRepository;
import com.example.armaria.repositories.ItemEstoqueRepository;

import jakarta.transaction.Transactional;

@Service
public class CriarEquipamentoUseCase {
  private final EqupamentoRepository equipamentoRepository;
  private final ItemEstoqueRepository itemEstoqueRepository;

  @Autowired
  public CriarEquipamentoUseCase(EqupamentoRepository equipamentoRepository,
      ItemEstoqueRepository itemEstoqueRepository) {
    this.equipamentoRepository = equipamentoRepository;
    this.itemEstoqueRepository = itemEstoqueRepository;
  }

  @Transactional
  public void execute(CriarEquipamentoComItemDTO equipamentoComItemDTO) {
    Optional<Equipamento> jaExiste = equipamentoRepository.findByNumSerie(equipamentoComItemDTO.numSerie());

    if (jaExiste.isPresent()) {
      throw new EquipamentoExistenteException(equipamentoComItemDTO.numSerie());
    }

    Equipamento equipamento = new Equipamento(equipamentoComItemDTO);
    equipamentoRepository.save(equipamento);

    ItemEstoque itemEstoque = new ItemEstoque(equipamento,
        equipamentoComItemDTO.quantidadeEmEstoque());
    itemEstoqueRepository.save(itemEstoque);
  }
}
