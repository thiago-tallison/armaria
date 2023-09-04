package com.example.armaria.controllers.equipamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.armaria.use_cases.equipamento.DeletarEquipamentoUseCase;

@RestController
@RequestMapping("/api/equipamento")
public class DeletarEquipamentoController {
  private final DeletarEquipamentoUseCase deletarEquipamentoUseCase;

  @Autowired
  public DeletarEquipamentoController(DeletarEquipamentoUseCase deletarEquipamentoUseCase) {
    this.deletarEquipamentoUseCase = deletarEquipamentoUseCase;
  }

  @DeleteMapping("{numSerie}")
  public ResponseEntity<Void> handle(@PathVariable String numSerie) {
    deletarEquipamentoUseCase.execute(numSerie);

    return ResponseEntity.ok().build();
  }
}
