package com.example.armaria.controllers.armory_keeper;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.armaria.use_cases.armory_keeper.ArmoryKeeperUpdateDTO;
import com.example.armaria.use_cases.armory_keeper.UpdateArmoryKeeperUseCase;

@RestController
@RequestMapping("/api/v1/armory_keepers")
public class UpdateArmoryKeeperController {
  private final UpdateArmoryKeeperUseCase updateArmoryKeeperUseCase;

  public UpdateArmoryKeeperController(UpdateArmoryKeeperUseCase updateArmoryKeeperUseCase) {
    this.updateArmoryKeeperUseCase = updateArmoryKeeperUseCase;
  }

  @PutMapping("{registrationNumber}")
  public ResponseEntity<Void> handle(@PathVariable String registrationNumber,
      @RequestBody ArmoryKeeperUpdateDTO armoryKeeperUpdateDTO) {

    updateArmoryKeeperUseCase.execute(registrationNumber, armoryKeeperUpdateDTO);
    return ResponseEntity.noContent().build();
  }

}
