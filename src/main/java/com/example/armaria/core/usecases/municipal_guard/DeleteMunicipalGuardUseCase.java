package com.example.armaria.core.usecases.municipal_guard;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.armaria.core.domain.MunicipalGuard;
import com.example.armaria.errors.MunicipalGuardNotFoundException;
import com.example.armaria.repositories.MunicipalGuardRepository;

@Service
public class DeleteMunicipalGuardUseCase {
  private MunicipalGuardRepository municipalGuardRepository;

  public DeleteMunicipalGuardUseCase(MunicipalGuardRepository municipalGuardRepository) {
    this.municipalGuardRepository = municipalGuardRepository;
  }

  public void execute(String registration) {
    Optional<MunicipalGuard> optionalArmorer = municipalGuardRepository.findByRegistrationNumber(registration);

    if (!optionalArmorer.isPresent()) {
      throw new MunicipalGuardNotFoundException(registration);
    }

    municipalGuardRepository.delete(optionalArmorer.get());
  }
}
