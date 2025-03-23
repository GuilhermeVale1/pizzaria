package com.sistema.pizzaria.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.pizzaria.models.ClienteModel;

public interface ClienteRepository extends JpaRepository<ClienteModel, UUID> {

}
