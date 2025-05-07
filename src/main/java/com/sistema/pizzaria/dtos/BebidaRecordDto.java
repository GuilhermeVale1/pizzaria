package com.sistema.pizzaria.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BebidaRecordDto(@NotBlank String name, String description, @NotNull Double price, @NotNull Integer volume, String tipo ) {

}
