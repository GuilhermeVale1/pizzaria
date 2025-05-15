package com.sistema.pizzaria.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.pizzaria.models.PedidoProductsModel;

public interface PedidosProductsRepository extends JpaRepository< PedidoProductsModel, UUID> {

}
