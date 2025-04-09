package com.sistema.pizzaria.dtos;

import jakarta.validation.constraints.NotBlank;

public record ResetPasswordDto(@NotBlank String password) {

}
