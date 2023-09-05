package com.example.armaria.controllers.equipamento;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.armaria.use_cases.equipamento.CriarEquipamentoComItemDTO;
import com.example.armaria.use_cases.equipamento.CriarEquipamentoUseCase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/equipamento")
public class CriarEquipamentoController {
  private final CriarEquipamentoUseCase criarEquipamentoUseCase;

  public CriarEquipamentoController(CriarEquipamentoUseCase criarEquipamentoUseCase) {
    this.criarEquipamentoUseCase = criarEquipamentoUseCase;
  }

  @PostMapping()
  public ResponseEntity<Void> handle(@Valid @RequestBody CriarEquipamentoComItemDTO data) {
    criarEquipamentoUseCase.execute(data);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
