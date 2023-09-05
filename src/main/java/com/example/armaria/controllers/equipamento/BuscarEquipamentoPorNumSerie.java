package com.example.armaria.controllers.equipamento;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.armaria.entities.Equipamento;
import com.example.armaria.use_cases.equipamento.BuscarEquipamentoPorNumSerieUseCase;

@RestController
@RequestMapping("/api/equipamento")
public class BuscarEquipamentoPorNumSerie {
  private final BuscarEquipamentoPorNumSerieUseCase buscarEquipamentoPorNumSerieUseCase;

  @Autowired
  public BuscarEquipamentoPorNumSerie(BuscarEquipamentoPorNumSerieUseCase buscarEquipamentoPorNumSerieUseCase) {
    this.buscarEquipamentoPorNumSerieUseCase = buscarEquipamentoPorNumSerieUseCase;
  }

  @GetMapping("{numSerie}")
  public ResponseEntity<Optional<Equipamento>> handle(@PathVariable String numSerie) {
    Optional<Equipamento> equOptional = buscarEquipamentoPorNumSerieUseCase.execute(numSerie);
    return ResponseEntity.status(HttpStatus.OK).body(equOptional);
  }
}