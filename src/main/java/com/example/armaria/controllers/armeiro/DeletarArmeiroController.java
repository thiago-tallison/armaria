package com.example.armaria.controllers.armeiro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.armaria.errors.ArmeiroNaoEncontradoException;
import com.example.armaria.use_cases.armeiro.DeletarArmeiroUseCase;

@RestController
@RequestMapping("/api/armeiro")
public class DeletarArmeiroController {
  private final DeletarArmeiroUseCase deletarArmeiroUseCase;

  @Autowired
  public DeletarArmeiroController(DeletarArmeiroUseCase deletarArmeiroUseCase) {
    this.deletarArmeiroUseCase = deletarArmeiroUseCase;
  }

  @DeleteMapping("{matricula}")
  public ResponseEntity<Void> handle(@PathVariable String matricula) {
    try {
      deletarArmeiroUseCase.execute(matricula);

      return ResponseEntity.status(204).build();
    } catch (Exception e) {
      if (e instanceof ArmeiroNaoEncontradoException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
      }
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(null);
    }
  }

}
