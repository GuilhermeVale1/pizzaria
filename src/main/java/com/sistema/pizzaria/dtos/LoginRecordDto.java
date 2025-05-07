package com.sistema.pizzaria.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginRecordDto(@NotBlank String email, @NotBlank String password) {

}
