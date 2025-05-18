package com.sistema.pizzaria.dtos;

import jakarta.validation.constraints.NotBlank;

public record EnderecoRecordDto( @NotBlank String cep, @NotBlank String bairro, @NotBlank String rua, @NotBlank String numero, String complemento) {

}
