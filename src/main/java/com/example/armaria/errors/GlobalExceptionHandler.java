package com.example.armaria.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

record ArmeiroNaoEncontradoExceptionResponse(
    String message) {
}

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ArmeiroNaoEncontradoException.class)
  public ResponseEntity<ArmeiroNaoEncontradoExceptionResponse> handleArmeiroNaoEncontradoException(
      ArmeiroNaoEncontradoException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ArmeiroNaoEncontradoExceptionResponse("Armeiro não encontrado."));
  }

}