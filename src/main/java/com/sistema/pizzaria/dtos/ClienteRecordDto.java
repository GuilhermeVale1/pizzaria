package com.sistema.pizzaria.dtos;

import jakarta.validation.constraints.NotBlank;

public record ClienteRecordDto(@NotBlank String cpf, @NotBlank String nome, String telefone, @NotBlank String endereco) {
	 

}
