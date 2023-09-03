package com.example.armaria.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

record MensagemDeErroGeral(
    String mensagem) {
}

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ArmeiroNaoEncontradoException.class)
  public ResponseEntity<MensagemDeErroGeral> handleArmeiroNaoEncontradoException(
      ArmeiroNaoEncontradoException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new MensagemDeErroGeral("Armeiro não encontrado."));
  }

  @ExceptionHandler(GuardaMunicipalNaoEncontradoException.class)
  public ResponseEntity<MensagemDeErroGeral> handleGuardaMunicipalNaoEncontradoException(
      GuardaMunicipalNaoEncontradoException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new MensagemDeErroGeral("Guarda municipal não encontrado."));
  }

}