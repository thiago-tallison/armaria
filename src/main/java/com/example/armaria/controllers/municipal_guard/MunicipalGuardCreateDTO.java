package com.example.armaria.controllers.municipal_guard;

public record MunicipalGuardCreateDTO(
        String registrationNumber,
        String name,
        String email,
        String phone) {
}