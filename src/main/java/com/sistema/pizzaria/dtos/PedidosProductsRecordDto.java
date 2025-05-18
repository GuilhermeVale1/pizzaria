package com.sistema.pizzaria.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record PedidosProductsRecordDto(@NotNull UUID idPedido, UUID idPizza, UUID idBebida  ) {

}
