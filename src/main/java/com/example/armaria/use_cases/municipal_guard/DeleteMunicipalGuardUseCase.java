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

  public void execute(String matricula) {
    Optional<MunicipalGuard> armeiro = municipalGuardRepository.findByRegistrationNumber(matricula);

    if (!armeiro.isPresent()) {
      throw new MunicipalGuardNotFoundException(matricula);
    }

    municipalGuardRepository.delete(armeiro.get());
  }
}
