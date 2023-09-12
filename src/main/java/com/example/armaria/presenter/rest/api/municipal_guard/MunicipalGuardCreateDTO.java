package com.example.armaria.presenter.rest.api.municipal_guard;

public record MunicipalGuardCreateDTO(
                String registrationNumber,
                String name,
                String email,
                String phone) {
}