package com.example.armaria.use_cases.armorer;

import jakarta.validation.constraints.NotEmpty;

public record ArmorerCreateDTO(
                @NotEmpty(message = "matricula must not be empty") String registrationNumber,
                @NotEmpty(message = "nome must not be empty") String name,
                @NotEmpty(message = "email must not be empty") String email,
                @NotEmpty(message = "telefone must not be empty") String phone,
                @NotEmpty(message = "login must not be empty") String login,
                @NotEmpty(message = "senha must not be empty") String password) {
}
