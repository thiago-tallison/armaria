package com.example.armaria.controllers.armory_keeper;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.armaria.use_cases.armory_keeper.DeleteArmoryKeeperUseCase;

@RestController
@RequestMapping("/api/v1/armory_keepers")
public class DeleteArmoryKeeperController {
  private final DeleteArmoryKeeperUseCase deleteArmoryKeeperUseCase;

  public DeleteArmoryKeeperController(DeleteArmoryKeeperUseCase deleteArmoryKeeperUseCase) {
    this.deleteArmoryKeeperUseCase = deleteArmoryKeeperUseCase;
  }

  @DeleteMapping("{registrationNumber}")
  public ResponseEntity<Void> handle(@PathVariable String registrationNumber) {
    deleteArmoryKeeperUseCase.execute(registrationNumber);
    return ResponseEntity.status(204).build();
  }

}
