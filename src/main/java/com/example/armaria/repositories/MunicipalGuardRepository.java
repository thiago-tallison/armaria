package com.example.armaria.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.armaria.core.domain.MunicipalGuard;

public interface MunicipalGuardRepository extends JpaRepository<MunicipalGuard, String> {
  Optional<MunicipalGuard> findByRegistrationNumber(String registrationNumber);

}
