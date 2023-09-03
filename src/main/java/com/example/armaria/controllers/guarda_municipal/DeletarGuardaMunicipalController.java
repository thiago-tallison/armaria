package com.example.armaria.controllers.guarda_municipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.armaria.errors.ArmeiroNaoEncontradoException;
import com.example.armaria.use_cases.guarda_municipal.DeletearGuardaMunicipalUseCase;

@RestController
@RequestMapping("/api/guarda-municipal")
public class DeletarGuardaMunicipalController {
  private final DeletearGuardaMunicipalUseCase deletearGuardaMunicipalUseCase;

  @Autowired
  public DeletarGuardaMunicipalController(DeletearGuardaMunicipalUseCase deletearGuardaMunicipalUseCase) {
    this.deletearGuardaMunicipalUseCase = deletearGuardaMunicipalUseCase;
  }

  @DeleteMapping("{matricula}")
  public ResponseEntity<Void> handle(@PathVariable String matricula) {
    try {
      deletearGuardaMunicipalUseCase.execute(matricula);

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
