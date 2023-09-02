package com.example.armaria.use_cases.guarda_municipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.armaria.controllers.guarda_municipal.CriarGuardaMunicipalDTO;
import com.example.armaria.entities.GuardaMunicipal;
import com.example.armaria.repositories.GuardaMunicipalRepository;

@Service
public class CriarGuardaMunicipalUseCase {

  private final GuardaMunicipalRepository guardaMunicipalRepository;

  @Autowired
  public CriarGuardaMunicipalUseCase(GuardaMunicipalRepository guardaMunicipalRepository) {
    this.guardaMunicipalRepository = guardaMunicipalRepository;
  }

  public void cadastrarGuardaMunicipal(CriarGuardaMunicipalDTO guardaMunicipalDTO) {
    // Validar dados do DTO, se necessário
    GuardaMunicipal guardaMunicipal = new GuardaMunicipal(guardaMunicipalDTO);
    guardaMunicipalRepository.save(guardaMunicipal);
  }
}
