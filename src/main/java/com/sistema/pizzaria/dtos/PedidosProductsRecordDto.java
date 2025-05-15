package com.sistema.pizzaria.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public record PedidosProductsRecordDto(@NotBlank UUID idPedido, UUID idPizza, UUID idBebida  ) {

}
