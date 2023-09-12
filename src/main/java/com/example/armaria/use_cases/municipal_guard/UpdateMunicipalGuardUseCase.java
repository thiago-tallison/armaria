package com.example.armaria.use_cases.municipal_guard;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.armaria.core.domain.MunicipalGuard;
import com.example.armaria.errors.MunicipalGuardNotFoundException;
import com.example.armaria.presenter.rest.api.municipal_guard.MunicipalGuardCreateDTO;
import com.example.armaria.repositories.MunicipalGuardRepository;

@Service
public class UpdateMunicipalGuardUseCase {
  private final MunicipalGuardRepository municipalGuardRepository;

  public UpdateMunicipalGuardUseCase(MunicipalGuardRepository municipalGuardRepository) {
    this.municipalGuardRepository = municipalGuardRepository;
  }

  public void execute(String registrationNumber, MunicipalGuardCreateDTO municipalGuardCreateDTO) {
    Optional<MunicipalGuard> guard = municipalGuardRepository.findByRegistrationNumber(registrationNumber);

    if (!guard.isPresent()) {
      throw new MunicipalGuardNotFoundException(registrationNumber);
    }

    MunicipalGuard municipalGuard = new MunicipalGuard(municipalGuardCreateDTO);
    municipalGuardRepository.save(municipalGuard);
  }

}
