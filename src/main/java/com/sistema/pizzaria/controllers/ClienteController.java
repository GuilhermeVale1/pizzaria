package com.sistema.pizzaria.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.pizzaria.dtos.ClienteRecordDto;
import com.sistema.pizzaria.models.ClienteModel;
import com.sistema.pizzaria.repositories.ClienteRepository;

import jakarta.validation.Valid;

@RestController
public class ClienteController {
	
	@Autowired
	ClienteRepository clienteRepository;
	
	
	@PostMapping("/clientes")
	public ResponseEntity<ClienteModel> saveCliente( @RequestBody @Valid ClienteRecordDto clientDto ){
		
		var clienteModel = new ClienteModel();
		BeanUtils.copyProperties(clientDto, clienteModel);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteRepository.save(clienteModel));
		
	}
	
	@GetMapping("/clientes")
	public ResponseEntity<List<ClienteModel>> getAll(){
		 List<ClienteModel> clientes = clienteRepository.findAll();
		 
		 return ResponseEntity.status(HttpStatus.OK).body(clientes);
	}
	
	@GetMapping("/clientes/{id}")
	public ResponseEntity<Object> getOne(@PathVariable(value = "id") UUID id){
		
		Optional<ClienteModel> cliente = clienteRepository.findById(id);
		
		if(cliente.isEmpty()) {
			return notFound();
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(cliente);
		
	}
	
	public ResponseEntity<Object> notFound(){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
	}
	
}
