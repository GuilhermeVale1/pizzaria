package com.sistema.pizzaria.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record PedidoRecordDto(@NotNull UUID id) {

}
