package com.example.armaria.errors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
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
        .body(new MensagemDeErroGeral(e.getMessage()));
  }

  @ExceptionHandler(MunicipalGuardNotFoundException.class)
  public ResponseEntity<MensagemDeErroGeral> handlemunicipalGuardNaoEncontradoException(
      MunicipalGuardNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new MensagemDeErroGeral(e.getMessage()));
  }

  @ExceptionHandler(EquipamentoNaoEncontradoException.class)
  public ResponseEntity<MensagemDeErroGeral> handleEquipamentoNaoEncontradoException(
      EquipamentoNaoEncontradoException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new MensagemDeErroGeral(e.getMessage()));
  }

  @ExceptionHandler(EquipamentoExistenteException.class)
  public ResponseEntity<MensagemDeErroGeral> handleEquipamentoExistenteException(
      EquipamentoExistenteException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new MensagemDeErroGeral(e.getMessage()));
  }

  @ExceptionHandler(ItemEstoqueNaoEncontradoException.class)
  public ResponseEntity<MensagemDeErroGeral> handleItemNaoEncontradoException(
      ItemEstoqueNaoEncontradoException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new MensagemDeErroGeral(e.getMessage()));
  }

  @ExceptionHandler(ItemEstoqueNaoDisponivelException.class)
  public ResponseEntity<MensagemDeErroGeral> handleItemEstoqueNaoDisponivelException(
      ItemEstoqueNaoDisponivelException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new MensagemDeErroGeral(e.getMessage()));
  }

  @ExceptionHandler(PaginaNaoExistenteException.class)
  public ResponseEntity<MensagemDeErroGeral> handlePaginaNaoExistenteException(
      PaginaNaoExistenteException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new MensagemDeErroGeral(e.getMessage()));
  }

  @ExceptionHandler(TamanhoDaPaginaNegativoException.class)
  public ResponseEntity<MensagemDeErroGeral> handlePaginaNaoExistenteException(
      TamanhoDaPaginaNegativoException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new MensagemDeErroGeral(e.getMessage()));
  }

  @ExceptionHandler(TamanhoDePaginaExcedeuOLimiteException.class)
  public ResponseEntity<MensagemDeErroGeral> handlePaginaNaoExistenteException(
      TamanhoDePaginaExcedeuOLimiteException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new MensagemDeErroGeral(e.getMessage()));
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
    String errorMessage = "Required request parameter '" + ex.getParameterName() + "' is not present";
    return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException e) {
    Map<String, List<String>> body = new HashMap<>();

    List<String> errors = e.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.toList());

    body.put("errors", errors);

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

}