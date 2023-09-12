package com.example.armaria.use_cases.municipal_guard;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.armaria.core.domain.MunicipalGuard;
import com.example.armaria.repositories.MunicipalGuardRepository;

@Service
public class GetMunicipalGuardByRegistrationUseCase {
  private final MunicipalGuardRepository guardRepository;

  public GetMunicipalGuardByRegistrationUseCase(MunicipalGuardRepository repo) {
    this.guardRepository = repo;
  }

  public Optional<MunicipalGuard> execute(String registration) {
    return guardRepository.findByRegistrationNumber(registration);
  }
}
