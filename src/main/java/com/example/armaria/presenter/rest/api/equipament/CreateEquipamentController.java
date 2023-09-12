package com.example.armaria.presenter.rest.api.equipament;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.armaria.use_cases.equipament.CreateEquipamentUseCase;
import com.example.armaria.use_cases.equipament.EquipamentCreateDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/equipaments")
public class CreateEquipamentController {
  private final CreateEquipamentUseCase createEquipamentUseCase;

  public CreateEquipamentController(CreateEquipamentUseCase createEquipamentUseCase) {
    this.createEquipamentUseCase = createEquipamentUseCase;
  }

  @PostMapping()
  public ResponseEntity<Void> handle(@Valid @RequestBody EquipamentCreateDTO data) {
    createEquipamentUseCase.execute(data);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
