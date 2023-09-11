package com.example.armaria.controllers.equipament;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.armaria.use_cases.equipament.DeleteEquipamentUseCase;

@RestController
@RequestMapping("/api/v1/equipaments")
public class DeleteEquipamentController {
  private final DeleteEquipamentUseCase deleteEquipamentUseCase;

  @Autowired
  public DeleteEquipamentController(DeleteEquipamentUseCase deleteEquipamentUseCase) {
    this.deleteEquipamentUseCase = deleteEquipamentUseCase;
  }

  @DeleteMapping("{serialNumber}")
  public ResponseEntity<Void> handle(@PathVariable String serialNumber) {
    deleteEquipamentUseCase.execute(serialNumber);

    return ResponseEntity.ok().build();
  }
}
