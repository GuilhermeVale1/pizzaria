package com.sistema.pizzaria.dtos;

import java.util.UUID;

public record PedidoResponseDto(UUID id, Boolean atendido, EnderecoRecordDto endereco) {
}
