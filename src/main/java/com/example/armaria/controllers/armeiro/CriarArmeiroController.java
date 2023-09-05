package com.example.armaria.controllers.armeiro;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.armaria.use_cases.armeiro.CriarArmeiroDTO;
import com.example.armaria.use_cases.armeiro.CriarArmeiroUseCase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/armeiro")
public class CriarArmeiroController {
  private final CriarArmeiroUseCase criarArmeiroUseCase;

  public CriarArmeiroController(CriarArmeiroUseCase criarArmeiroUseCase) {
    this.criarArmeiroUseCase = criarArmeiroUseCase;
  }

  @PostMapping("")
  public ResponseEntity<Object> handle(@Valid @RequestBody CriarArmeiroDTO dto) {
    criarArmeiroUseCase.execute(dto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
