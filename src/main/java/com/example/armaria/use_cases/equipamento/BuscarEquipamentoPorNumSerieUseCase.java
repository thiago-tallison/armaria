package com.example.armaria.use_cases.equipamento;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.armaria.entities.Equipamento;
import com.example.armaria.errors.EquipamentoNaoEncontradoException;
import com.example.armaria.repositories.EquipamentoRepository;
import com.example.armaria.repositories.ItemEstoqueRepository;

@Service
public class BuscarEquipamentoPorNumSerieUseCase {
  private final EquipamentoRepository equipamentoRepository;

  @Autowired
  public BuscarEquipamentoPorNumSerieUseCase(EquipamentoRepository equipamentoRepository,
      ItemEstoqueRepository itemEstoqueRepository) {
    this.equipamentoRepository = equipamentoRepository;
  }

  public Optional<Equipamento> execute(String numSerie) {
    Optional<Equipamento> equipamento = equipamentoRepository.findByNumSerie(numSerie);

    if (!equipamento.isPresent()) {
      throw new EquipamentoNaoEncontradoException();
    }

    return equipamento;
  }

}
