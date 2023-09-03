package com.example.armaria.controllers.armeiro;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

  @Autowired
  public BuscarArmeiroPorMatriculaController(BuscarArmeiroPorMatriculaUseCase buscarArmeiroPorMatriculaUseCase) {
    this.buscarArmeiroPorMatriculaUseCase = buscarArmeiroPorMatriculaUseCase;
  }

  @GetMapping("{matricula}")
  public ResponseEntity<Armeiro> handle(@PathVariable String matricula) {
    try {
      Optional<Armeiro> armeiro = buscarArmeiroPorMatriculaUseCase.execute(matricula);
      if (armeiro.isPresent()) {
        return ResponseEntity.ok().body(armeiro.get());
      }

      return ResponseEntity.notFound().build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(null);
    }
  }

}
