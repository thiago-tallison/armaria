package com.example.armaria.controllers.armorer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.armaria.use_cases.armorer.ArmorerCreateDTO;
import com.example.armaria.use_cases.armorer.CreateArmorerUseCase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/armorers")
public class CreateArmorerController {
  private final CreateArmorerUseCase createArmorerUseCase;

  public CreateArmorerController(CreateArmorerUseCase createArmorerUseCase) {
    this.createArmorerUseCase = createArmorerUseCase;
  }

  @PostMapping("")
  public ResponseEntity<Object> handle(@Valid @RequestBody ArmorerCreateDTO dto) {
    createArmorerUseCase.execute(dto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
