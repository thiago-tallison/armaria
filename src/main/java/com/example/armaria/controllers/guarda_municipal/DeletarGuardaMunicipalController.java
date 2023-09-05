package com.example.armaria.controllers.guarda_municipal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.armaria.use_cases.guarda_municipal.DeletearGuardaMunicipalUseCase;

@RestController
@RequestMapping("/api/guarda-municipal")
public class DeletarGuardaMunicipalController {
  private final DeletearGuardaMunicipalUseCase deletearGuardaMunicipalUseCase;

  public DeletarGuardaMunicipalController(DeletearGuardaMunicipalUseCase deletearGuardaMunicipalUseCase) {
    this.deletearGuardaMunicipalUseCase = deletearGuardaMunicipalUseCase;
  }

  @DeleteMapping("{matricula}")
  public ResponseEntity<Void> handle(@PathVariable String matricula) {
    deletearGuardaMunicipalUseCase.execute(matricula);
    return ResponseEntity.status(204).build();
  }

}
