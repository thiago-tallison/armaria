package com.example.armaria.use_cases.armeiro;

public record CriarArmeiroDTO(
    String matricula,
    String nome,
    String email,
    String telefone,
    String login,
    String senha) {
}
