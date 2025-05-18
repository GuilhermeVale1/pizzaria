package com.sistema.pizzaria.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.pizzaria.models.ClienteModel;
import com.sistema.pizzaria.models.EnderecoModel;

public interface EnderecoRepository extends JpaRepository<EnderecoModel, UUID> {
	List<EnderecoModel> findByClienteModel(ClienteModel clienteModel);
	
	Optional<EnderecoModel> findByClienteModelAndPrincipal(ClienteModel clienteModel, Boolean principal);
}
