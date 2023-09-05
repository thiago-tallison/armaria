package com.example.armaria.controllers.armeiro;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.armaria.entities.Armeiro;
import com.example.armaria.use_cases.armeiro.BuscarArmeiroPorMatriculaUseCase;

@RestController
@RequestMapping("/api/armeiro")
public class BuscarArmeiroPorMatriculaController {
  private final BuscarArmeiroPorMatriculaUseCase buscarArmeiroPorMatriculaUseCase;

  public BuscarArmeiroPorMatriculaController(BuscarArmeiroPorMatriculaUseCase buscarArmeiroPorMatriculaUseCase) {
    this.buscarArmeiroPorMatriculaUseCase = buscarArmeiroPorMatriculaUseCase;
  }

  @GetMapping("{matricula}")
  public ResponseEntity<Optional<Armeiro>> handle(@PathVariable String matricula) {
    Optional<Armeiro> armeiro = buscarArmeiroPorMatriculaUseCase.execute(matricula);

    return ResponseEntity.ok().body(armeiro);
  }

}
