package com.example.armaria.controllers.guarda_municipal;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.armaria.entities.GuardaMunicipal;
import com.example.armaria.use_cases.guarda_municipal.BuscarGuardaMunicipalPorMatriculaUseCase;

@RestController
@RequestMapping("/api/guarda-municipal")
public class BuscarGuardaMunicipalPorMatriculaController {
  private final BuscarGuardaMunicipalPorMatriculaUseCase buscarGuardaMunicipalPorMatriculaUseCase;

  @Autowired
  public BuscarGuardaMunicipalPorMatriculaController(
      BuscarGuardaMunicipalPorMatriculaUseCase buscarGuardaMunicipalPorMatriculaUseCase) {
    this.buscarGuardaMunicipalPorMatriculaUseCase = buscarGuardaMunicipalPorMatriculaUseCase;
  }

  @GetMapping("{matricula}")
  public ResponseEntity<GuardaMunicipal> handle(@PathVariable String matricula) {
    try {
      Optional<GuardaMunicipal> guardaMunicipal = buscarGuardaMunicipalPorMatriculaUseCase.execute(matricula);
      if (guardaMunicipal.isPresent()) {
        return ResponseEntity.ok().body(guardaMunicipal.get());
      }

      return ResponseEntity.notFound().build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(null);
    }
  }

}
