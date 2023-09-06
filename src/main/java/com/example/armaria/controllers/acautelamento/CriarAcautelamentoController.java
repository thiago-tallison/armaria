package com.example.armaria.controllers.acautelamento;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.armaria.dtos.acautelamento.CriarAcautelamentoDTO;
import com.example.armaria.use_cases.acautelamento.CriarAcautelamentoUseCase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/acautelamento")
public class CriarAcautelamentoController {
  private final CriarAcautelamentoUseCase criarAcautelamentoUseCase;

  public CriarAcautelamentoController(CriarAcautelamentoUseCase criarAcautelamentoUseCase) {
    this.criarAcautelamentoUseCase = criarAcautelamentoUseCase;
  }

  @PostMapping
  public ResponseEntity<Void> execute(@Valid @RequestBody CriarAcautelamentoDTO data) {
    criarAcautelamentoUseCase.execute(data);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
