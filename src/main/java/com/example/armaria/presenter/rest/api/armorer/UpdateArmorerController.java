package com.example.armaria.presenter.rest.api.armorer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.armaria.use_cases.armorer.ArmorerUpdateDTO;
import com.example.armaria.use_cases.armorer.UpdateArmorerUseCase;

@RestController
@RequestMapping("/api/v1/armorers")
public class UpdateArmorerController {
  private final UpdateArmorerUseCase updateArmorerUseCase;

  public UpdateArmorerController(UpdateArmorerUseCase updateArmorerUseCase) {
    this.updateArmorerUseCase = updateArmorerUseCase;
  }

  @PutMapping("{registrationNumber}")
  public ResponseEntity<Void> handle(@PathVariable String registrationNumber,
      @RequestBody ArmorerUpdateDTO armorerUpdateDTO) {

    updateArmorerUseCase.execute(registrationNumber, armorerUpdateDTO);
    return ResponseEntity.noContent().build();
  }

}
