package com.example.armaria.controllers.armorer;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.armaria.core.domain.Armorer;
import com.example.armaria.use_cases.armorer.GetArmorerByRegistrationUseCase;

@RestController
@RequestMapping("/api/v1/armorers")
public class GetArmorerByRegistrationController {
  private final GetArmorerByRegistrationUseCase getArmorerByRegistrationUseCase;

  public GetArmorerByRegistrationController(
      GetArmorerByRegistrationUseCase getArmorerByRegistrationUseCase) {
    this.getArmorerByRegistrationUseCase = getArmorerByRegistrationUseCase;
  }

  @GetMapping("{registrationNumber}")
  public ResponseEntity<Optional<Armorer>> handle(@PathVariable String registrationNumber) {
    Optional<Armorer> optionalArmorer = getArmorerByRegistrationUseCase.execute(registrationNumber);

    return ResponseEntity.ok().body(optionalArmorer);
  }

}
