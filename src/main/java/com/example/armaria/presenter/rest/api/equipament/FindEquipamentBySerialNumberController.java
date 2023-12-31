package com.example.armaria.presenter.rest.api.equipament;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.armaria.core.domain.Equipament;
import com.example.armaria.core.usecases.equipament.FindEquipamentBySerialNumberUseCase;

@RestController
@RequestMapping("/api/v1/equipaments")
public class FindEquipamentBySerialNumberController {
  private final FindEquipamentBySerialNumberUseCase findEquipamentBySerialNumberUseCase;

  public FindEquipamentBySerialNumberController(
      FindEquipamentBySerialNumberUseCase findEquipamentBySerialNumberUseCase) {
    this.findEquipamentBySerialNumberUseCase = findEquipamentBySerialNumberUseCase;
  }

  @GetMapping("{serialNumber}")
  public ResponseEntity<Optional<Equipament>> handle(@PathVariable String serialNumber) {
    Optional<Equipament> optionalEquipament = findEquipamentBySerialNumberUseCase.execute(serialNumber);
    return ResponseEntity.status(HttpStatus.OK).body(optionalEquipament);
  }
}