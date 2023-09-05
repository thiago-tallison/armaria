package com.example.armaria.use_cases.item_estoque;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.armaria.entities.Equipamento;
import com.example.armaria.entities.ItemEstoque;
import com.example.armaria.errors.EquipamentoNaoEncontradoException;
import com.example.armaria.repositories.EquipamentoRepository;
import com.example.armaria.repositories.ItemEstoqueRepository;

@Service
public class BuscarItemEstoquePorEquipamentoUseCase {
  private final ItemEstoqueRepository itemEstoqueRepository;
  private final EquipamentoRepository equipamentoRepository;

  public BuscarItemEstoquePorEquipamentoUseCase(
      ItemEstoqueRepository itemEstoqueRepository,
      EquipamentoRepository equipamentoRepository) {
    this.itemEstoqueRepository = itemEstoqueRepository;
    this.equipamentoRepository = equipamentoRepository;
  }

  public Optional<ItemEstoque> execute(String numSerie) {
    Optional<Equipamento> eqOptional = equipamentoRepository
        .findByNumSerie(numSerie);

    if (!eqOptional.isPresent() || numSerie == null) {
      throw new EquipamentoNaoEncontradoException(numSerie);
    }

    Optional<ItemEstoque> itOptional = itemEstoqueRepository
        .findByEquipamento(eqOptional.get());

    return itOptional;
  }

}
