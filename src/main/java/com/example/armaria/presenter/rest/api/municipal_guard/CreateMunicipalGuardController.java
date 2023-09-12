package com.example.armaria.presenter.rest.api.municipal_guard;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.armaria.use_cases.municipal_guard.CreateMunicipalGuardUseCase;

@RestController
@RequestMapping("/api/v1/municipal_guards")
public class CreateMunicipalGuardController {
  private final CreateMunicipalGuardUseCase createMunicipalGuardUseCase;

  public CreateMunicipalGuardController(CreateMunicipalGuardUseCase createMunicipalGuardUseCase) {
    this.createMunicipalGuardUseCase = createMunicipalGuardUseCase;
  }

  @PostMapping("/create")
  public ResponseEntity<String> handle(@RequestBody MunicipalGuardCreateDTO municipalGuardCreateDTO) {
    try {
      createMunicipalGuardUseCase.execute(municipalGuardCreateDTO);
      return ResponseEntity.status(HttpStatus.CREATED).body("Guarda Municipal cadastrado com sucesso.");
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Erro ao cadastrar Guarda Municipal" + e.getMessage());
    }
  }
}
