package com.example.armaria.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.armaria.entities.GuardaMunicipal;

public interface GuardaMunicipalRepository extends JpaRepository<GuardaMunicipal, String> {
  Optional<GuardaMunicipal> findByMatricula(String matricula);

}
