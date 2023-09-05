package com.example.armaria.controllers.armeiro;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.armaria.use_cases.armeiro.AtualizarArmeiroDTO;
import com.example.armaria.use_cases.armeiro.AtualizarArmeiroUseCase;

@RestController
@RequestMapping("/api/armeiro")
public class AtualizarArmeiroController {
  private final AtualizarArmeiroUseCase atualizarArmeiroUseCase;

  public AtualizarArmeiroController(AtualizarArmeiroUseCase atualizarArmeiroUseCase) {
    this.atualizarArmeiroUseCase = atualizarArmeiroUseCase;
  }

  @PutMapping("{matricula}")
  public ResponseEntity<Void> handle(@PathVariable String matricula,
      @RequestBody AtualizarArmeiroDTO armeiroDto) {

    atualizarArmeiroUseCase.execute(matricula, armeiroDto);
    return ResponseEntity.noContent().build();
  }

}
