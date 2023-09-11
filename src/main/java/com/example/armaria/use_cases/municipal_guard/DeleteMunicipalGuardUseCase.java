package com.example.armaria.use_cases.municipal_guard;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.armaria.entities.MunicipalGuard;
import com.example.armaria.errors.MunicipalGuardNotFoundException;
import com.example.armaria.repositories.MunicipalGuardRepository;

@Service
public class DeleteMunicipalGuardUseCase {
  private MunicipalGuardRepository municipalGuardRepository;

  @Autowired
  public DeleteMunicipalGuardUseCase(MunicipalGuardRepository municipalGuardRepository) {
    this.municipalGuardRepository = municipalGuardRepository;
  }

  public void execute(String registration) {
    Optional<MunicipalGuard> optionalArmoryKeeper = municipalGuardRepository.findByRegistrationNumber(registration);

    if (!optionalArmoryKeeper.isPresent()) {
      throw new MunicipalGuardNotFoundException(registration);
    }

    municipalGuardRepository.delete(optionalArmoryKeeper.get());
  }
}
