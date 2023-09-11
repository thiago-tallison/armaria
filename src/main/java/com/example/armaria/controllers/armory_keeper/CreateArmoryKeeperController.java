package com.example.armaria.controllers.armory_keeper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.armaria.use_cases.armory_keeper.ArmoryKeeperCreateDTO;
import com.example.armaria.use_cases.armory_keeper.CreateArmoryKeeperUseCase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/armory_keepers")
public class CreateArmoryKeeperController {
  private final CreateArmoryKeeperUseCase createArmoryKeeperUseCase;

  public CreateArmoryKeeperController(CreateArmoryKeeperUseCase createArmoryKeeperUseCase) {
    this.createArmoryKeeperUseCase = createArmoryKeeperUseCase;
  }

  @PostMapping("")
  public ResponseEntity<Object> handle(@Valid @RequestBody ArmoryKeeperCreateDTO dto) {
    createArmoryKeeperUseCase.execute(dto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
