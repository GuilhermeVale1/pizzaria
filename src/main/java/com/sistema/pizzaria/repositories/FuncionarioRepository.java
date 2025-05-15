package com.sistema.pizzaria.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.sistema.pizzaria.models.FuncionarioModel;



public interface FuncionarioRepository extends JpaRepository<FuncionarioModel , UUID> {
	UserDetails findByEmail(String email);
}
