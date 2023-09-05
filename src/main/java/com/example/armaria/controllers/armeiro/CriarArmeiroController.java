package com.example.armaria.controllers.armeiro;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.armaria.use_cases.armeiro.CriarArmeiroDTO;
import com.example.armaria.use_cases.armeiro.CriarArmeiroUseCase;

@RestController
@RequestMapping("/api/armeiro")
public class CriarArmeiroController {
  private final CriarArmeiroUseCase criarArmeiroUseCase;

  public CriarArmeiroController(CriarArmeiroUseCase criarArmeiroUseCase) {
    this.criarArmeiroUseCase = criarArmeiroUseCase;
  }

  @PostMapping("")
  public ResponseEntity<String> handle(@RequestBody CriarArmeiroDTO dto) {
    try {
      criarArmeiroUseCase.execute(dto);
      return ResponseEntity.status(HttpStatus.CREATED).body("Guarda Municipal cadastrado com sucesso.");
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Erro ao cadastrar Armeiro");
    }
  }
}
