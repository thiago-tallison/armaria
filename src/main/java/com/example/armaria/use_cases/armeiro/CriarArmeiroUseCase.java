package com.example.armaria.use_cases.armeiro;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.armaria.entities.Armeiro;
import com.example.armaria.errors.ArmeiroJaCadastradoException;
import com.example.armaria.repositories.ArmeiroRepository;
import com.example.armaria.utils.CifradorDeSenha;

@Service
public class CriarArmeiroUseCase {
  private final ArmeiroRepository armeiroRepository;
  private final CifradorDeSenha cifradorDeSenha;

  @Autowired
  public CriarArmeiroUseCase(ArmeiroRepository armeiroRepository,
      CifradorDeSenha cifradorDeSenha) {
    this.armeiroRepository = armeiroRepository;
    this.cifradorDeSenha = cifradorDeSenha;
  }

  public void execute(CriarArmeiroDTO criarArmeiroDTO) {
    Optional<Armeiro> optionalArmeiro = armeiroRepository.findByMatricula(criarArmeiroDTO.matricula());

    if (optionalArmeiro.isPresent()) {
      throw new ArmeiroJaCadastradoException(criarArmeiroDTO.matricula());
    }

    String hashedPassword = cifradorDeSenha.cifrar(criarArmeiroDTO.senha());

    Armeiro armeiro = new Armeiro(criarArmeiroDTO);
    armeiro.setSenha(hashedPassword);

    armeiroRepository.save(armeiro);
  }
}
