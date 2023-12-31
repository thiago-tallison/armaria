package com.example.armaria.core.usecases.municipal_guard;

import org.springframework.stereotype.Service;

import com.example.armaria.core.domain.MunicipalGuard;
import com.example.armaria.presenter.rest.api.municipal_guard.MunicipalGuardCreateDTO;
import com.example.armaria.repositories.MunicipalGuardRepository;

@Service
public class CreateMunicipalGuardUseCase {

  private final MunicipalGuardRepository municipalGuardRepository;

  public CreateMunicipalGuardUseCase(MunicipalGuardRepository municipalGuardRepository) {
    this.municipalGuardRepository = municipalGuardRepository;
  }

  public void execute(MunicipalGuardCreateDTO municipalGuardCreateDTO) {
    // Validar dados do DTO, se necessário
    MunicipalGuard municipalGuard = new MunicipalGuard(municipalGuardCreateDTO);
    municipalGuardRepository.save(municipalGuard);
  }
}
