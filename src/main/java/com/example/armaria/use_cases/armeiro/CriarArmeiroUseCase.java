package com.example.armaria.use_cases.armeiro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.armaria.entities.Armeiro;
import com.example.armaria.repositories.ArmeiroRepository;

@Service
public class CriarArmeiroUseCase {
  private final ArmeiroRepository armeiroRepository;

  @Autowired
  public CriarArmeiroUseCase(ArmeiroRepository armeiroRepository) {
    this.armeiroRepository = armeiroRepository;
  }

  public void execute(CriarArmeiroDTO criarArmeiroDTO) {
    Armeiro armeiro = new Armeiro(criarArmeiroDTO);
    armeiroRepository.save(armeiro);
  }
}
