package com.example.armaria.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class CifradorDeSenhaBcrypt implements CifradorDeSenha {

  @Override
  public String cifrar(String s) {
    String hashedPass = BCrypt.hashpw(s, BCrypt.gensalt());

    return hashedPass;
  }

}
