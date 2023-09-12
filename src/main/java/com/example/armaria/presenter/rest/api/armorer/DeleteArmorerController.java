package com.example.armaria.presenter.rest.api.armorer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.armaria.core.usecases.armorer.DeleteArmorerUseCase;

@RestController
@RequestMapping("/api/v1/armorers")
public class DeleteArmorerController {
  private final DeleteArmorerUseCase deleteArmorerUseCase;

  public DeleteArmorerController(DeleteArmorerUseCase deleteArmorerUseCase) {
    this.deleteArmorerUseCase = deleteArmorerUseCase;
  }

  @DeleteMapping("{registrationNumber}")
  public ResponseEntity<Void> handle(@PathVariable String registrationNumber) {
    deleteArmorerUseCase.execute(registrationNumber);
    return ResponseEntity.status(204).build();
  }

}
