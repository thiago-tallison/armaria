package com.example.armaria.use_cases.municipal_guard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.armaria.controllers.municipal_guard.MunicipalGuardCreateDTO;
import com.example.armaria.entities.MunicipalGuard;
import com.example.armaria.repositories.MunicipalGuardRepository;

@Service
public class CreateMunicipalGuardUseCase {

  private final MunicipalGuardRepository municipalGuardRepository;

  @Autowired
  public CreateMunicipalGuardUseCase(MunicipalGuardRepository municipalGuardRepository) {
    this.municipalGuardRepository = municipalGuardRepository;
  }

  public void execute(MunicipalGuardCreateDTO municipalGuardCreateDTO) {
    // Validar dados do DTO, se necessário
    MunicipalGuard municipalGuard = new MunicipalGuard(municipalGuardCreateDTO);
    municipalGuardRepository.save(municipalGuard);
  }
}
