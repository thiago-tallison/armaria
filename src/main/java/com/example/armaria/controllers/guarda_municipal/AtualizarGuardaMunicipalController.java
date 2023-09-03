package com.example.armaria.controllers.guarda_municipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.armaria.use_cases.guarda_municipal.AtualizarGuardaMunicipalUseCase;

@RestController
@RequestMapping("/api/guarda-municipal")
public class AtualizarGuardaMunicipalController {
  private final AtualizarGuardaMunicipalUseCase atualizarGuardaMunicipalUseCase;

  @Autowired
  public AtualizarGuardaMunicipalController(AtualizarGuardaMunicipalUseCase atualizarGuardaMunicipalUseCase) {
    this.atualizarGuardaMunicipalUseCase = atualizarGuardaMunicipalUseCase;
  }

  @PutMapping("{matricula}")
  public ResponseEntity<Void> cadastrarGuardaMunicipal(@PathVariable String matricula,
      @RequestBody CriarGuardaMunicipalDTO criarGuardaMunicipalDTO) {

    atualizarGuardaMunicipalUseCase.execute(matricula, criarGuardaMunicipalDTO);
    return ResponseEntity.noContent().build();
  }

}
