package com.example.armaria.controllers.guarda_municipal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.armaria.use_cases.guarda_municipal.CriarGuardaMunicipalUseCase;

@RestController
@RequestMapping("/api/guarda-municipal")
public class CriarGuardaMunicipalController {
  private final CriarGuardaMunicipalUseCase guardaMunicipalUseCase;

  public CriarGuardaMunicipalController(CriarGuardaMunicipalUseCase guardaMunicipalUseCase) {
    this.guardaMunicipalUseCase = guardaMunicipalUseCase;
  }

  @PostMapping("/cadastrar")
  public ResponseEntity<String> cadastrarGuardaMunicipal(@RequestBody CriarGuardaMunicipalDTO criarGuardaMunicipalDTO) {
    try {
      guardaMunicipalUseCase.cadastrarGuardaMunicipal(criarGuardaMunicipalDTO);
      return ResponseEntity.status(HttpStatus.CREATED).body("Guarda Municipal cadastrado com sucesso.");
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Erro ao cadastrar Guarda Municipal" + e.getMessage());
    }
  }
}
