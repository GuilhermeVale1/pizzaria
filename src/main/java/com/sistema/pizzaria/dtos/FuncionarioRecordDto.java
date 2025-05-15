package com.sistema.pizzaria.dtos;

import jakarta.validation.constraints.NotBlank;

public record FuncionarioRecordDto(@NotBlank String cpf, @NotBlank String nome, @NotBlank String email, String telefone, @NotBlank String password) {

}
