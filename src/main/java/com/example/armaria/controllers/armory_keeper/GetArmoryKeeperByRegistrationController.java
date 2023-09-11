package com.example.armaria.controllers.armory_keeper;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.armaria.entities.ArmoryKepper;
import com.example.armaria.use_cases.armory_keeper.GetArmoryKeeperByRegistrationUseCase;

@RestController
@RequestMapping("/api/v1/armory_keepers")
public class GetArmoryKeeperByRegistrationController {
  private final GetArmoryKeeperByRegistrationUseCase getArmoryKeeperByRegistrationUseCase;

  public GetArmoryKeeperByRegistrationController(
      GetArmoryKeeperByRegistrationUseCase getArmoryKeeperByRegistrationUseCase) {
    this.getArmoryKeeperByRegistrationUseCase = getArmoryKeeperByRegistrationUseCase;
  }

  @GetMapping("{registrationNumber}")
  public ResponseEntity<Optional<ArmoryKepper>> handle(@PathVariable String registrationNumber) {
    Optional<ArmoryKepper> optionalArmoryKeeper = getArmoryKeeperByRegistrationUseCase.execute(registrationNumber);

    return ResponseEntity.ok().body(optionalArmoryKeeper);
  }

}
