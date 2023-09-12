package com.example.armaria.controllers.municipal_guard;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.armaria.core.domain.MunicipalGuard;
import com.example.armaria.use_cases.municipal_guard.GetMunicipalGuardByRegistrationUseCase;

@RestController
@RequestMapping("/api/v1/municipal_guards")
public class GetMunicipalGuardByRegistrationController {
  private final GetMunicipalGuardByRegistrationUseCase getMunicipalGuardByRegistrationUseCase;

  public GetMunicipalGuardByRegistrationController(
      GetMunicipalGuardByRegistrationUseCase getMunicipalGuardByRegistrationUseCase) {
    this.getMunicipalGuardByRegistrationUseCase = getMunicipalGuardByRegistrationUseCase;
  }

  @GetMapping("{registrationNumber}")
  public ResponseEntity<MunicipalGuard> handle(@PathVariable String registrationNumber) {
    try {
      Optional<MunicipalGuard> optionalGuard = getMunicipalGuardByRegistrationUseCase.execute(registrationNumber);
      if (optionalGuard.isPresent()) {
        return ResponseEntity.ok().body(optionalGuard.get());
      }

      return ResponseEntity.notFound().build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(null);
    }
  }

}
