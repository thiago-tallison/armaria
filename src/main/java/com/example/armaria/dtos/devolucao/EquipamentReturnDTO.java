package com.example.armaria.dtos.devolucao;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EquipamentReturnDTO(
                @NotNull(message = "dataDevolucao cannot be null") LocalDateTime dataDevolucao,
                @NotBlank(message = "municipalGuardRegistration cannot be null") String municipalGuardRegistration,
                @NotNull(message = "checkoutId cannot be null") Long checkoutId,
                @NotBlank(message = "armorerRegistration cannot be null") String armorerRegistration,
                @NotNull(message = "itensAcautelados cannot be null") List<ItemDevolvidoDTO> itensDevolvidos) {

        public EquipamentReturnDTO withArmorerRegistration(String armorerRegistration) {
                return new EquipamentReturnDTO(dataDevolucao, municipalGuardRegistration, checkoutId,
                                armorerRegistration,
                                itensDevolvidos);
        }

        public EquipamentReturnDTO withMunicipalGuardRegistration(String municipalGuardRegistration) {
                return new EquipamentReturnDTO(dataDevolucao, municipalGuardRegistration, checkoutId,
                                armorerRegistration,
                                itensDevolvidos);
        }
}