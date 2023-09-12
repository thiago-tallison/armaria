package com.example.armaria.presenter.rest.api.municipal_guard;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.armaria.use_cases.municipal_guard.UpdateMunicipalGuardUseCase;

@RestController
@RequestMapping("/api/v1/municipal_guards")
public class UpdateMunicipalGuardController {
  private final UpdateMunicipalGuardUseCase updateMunicipalGuardUseCase;

  public UpdateMunicipalGuardController(UpdateMunicipalGuardUseCase updateMunicipalGuardUseCase) {
    this.updateMunicipalGuardUseCase = updateMunicipalGuardUseCase;
  }

  @PutMapping("{registrationNumber}")
  public ResponseEntity<Void> registerMunicipalGuard(@PathVariable String registrationNumber,
      @RequestBody MunicipalGuardCreateDTO municipalGuardCreateDTO) {

    updateMunicipalGuardUseCase.execute(registrationNumber, municipalGuardCreateDTO);
    return ResponseEntity.noContent().build();
  }

}
