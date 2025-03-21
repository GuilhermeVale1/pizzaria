package com.sistema.pizzaria.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PizzaRecordDto(@NotBlank String name, String description, @NotNull Double price, String tamanho, String ingredientes ) {
	
}
