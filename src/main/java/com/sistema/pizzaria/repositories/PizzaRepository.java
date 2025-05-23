package com.sistema.pizzaria.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.pizzaria.models.PizzaModel;


@Repository
public interface PizzaRepository extends JpaRepository<PizzaModel, UUID> {
	
}
