package com.sistema.pizzaria.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.sistema.pizzaria.models.ClienteModel;
import java.util.List;


public interface ClienteRepository extends JpaRepository<ClienteModel, UUID> {
	UserDetails findByEmail(String email);
}
