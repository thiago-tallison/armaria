package com.example.armaria.controllers.municipal_guard;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.armaria.use_cases.municipal_guard.DeleteMunicipalGuardUseCase;

@RestController
@RequestMapping("/api/v1/municipal_guards")
public class DeleteMunicipalGuardController {
  private final DeleteMunicipalGuardUseCase deleteMunicipalGuardUseCase;

  public DeleteMunicipalGuardController(DeleteMunicipalGuardUseCase deleteMunicipalGuardUseCase) {
    this.deleteMunicipalGuardUseCase = deleteMunicipalGuardUseCase;
  }

  @DeleteMapping("{registrationNumber}")
  public ResponseEntity<Void> handle(@PathVariable String registrationNumber) {
    deleteMunicipalGuardUseCase.execute(registrationNumber);
    return ResponseEntity.status(204).build();
  }

}
