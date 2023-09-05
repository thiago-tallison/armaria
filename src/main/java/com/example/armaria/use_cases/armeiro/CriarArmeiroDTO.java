package com.example.armaria.use_cases.armeiro;

import jakarta.validation.constraints.NotEmpty;

public record CriarArmeiroDTO(
        @NotEmpty(message = "matricula must not be empty") String matricula,
        @NotEmpty(message = "nome must not be empty") String nome,
        @NotEmpty(message = "email must not be empty") String email,
        @NotEmpty(message = "telefone must not be empty") String telefone,
        @NotEmpty(message = "login must not be empty") String login,
        @NotEmpty(message = "senha must not be empty") String senha) {
}
