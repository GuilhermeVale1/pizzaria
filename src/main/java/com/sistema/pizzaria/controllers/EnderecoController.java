package com.sistema.pizzaria.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.pizzaria.dtos.EnderecoRecordDto;
import com.sistema.pizzaria.models.ClienteModel;
import com.sistema.pizzaria.models.EnderecoModel;
import com.sistema.pizzaria.repositories.ClienteRepository;
import com.sistema.pizzaria.repositories.EnderecoRepository;

import jakarta.validation.Valid;


@RestController
public class EnderecoController {
	
	@Autowired
	EnderecoRepository enderecoRepository;
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@PostMapping("/endereco")
	public ResponseEntity<Object> createEndereco(@RequestBody EnderecoRecordDto enderecoDto){
		
		
		var authentication = SecurityContextHolder.getContext().getAuthentication();
		
		 if (authentication == null || !(authentication.getPrincipal() instanceof ClienteModel clienteModel)) {
		     return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado.");
		 }
		
		
		
		List<EnderecoModel> enderecos = enderecoRepository.findByClienteModel(clienteModel);
		EnderecoModel enderecoModel = new EnderecoModel(clienteModel, enderecoDto.cep(), enderecoDto.bairro(), enderecoDto.rua(), enderecoDto.numero(), enderecoDto.complemento());
		
		if(enderecos.isEmpty()) {
			enderecoModel.setPrincipal(true);
		}
		
		
		enderecoRepository.save(enderecoModel);
		return ResponseEntity.status(HttpStatus.CREATED).body("Endereço cadastrado com sucesso");
		
	}
	
	
	@GetMapping("/endereco")
	public ResponseEntity<Object> getAll(){
		
		var authentication = SecurityContextHolder.getContext().getAuthentication();
		
		 if (authentication == null || !(authentication.getPrincipal() instanceof ClienteModel clienteModel)) {
		     return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado.");
		 }
		 
		 

		 List<EnderecoModel> enderecos = enderecoRepository.findByClienteModel(clienteModel);
		 if(!enderecos.isEmpty()) {
			 for(EnderecoModel endereco : enderecos) {
				 UUID id = endereco.getId();
				 
				 endereco.add(linkTo(methodOn(EnderecoController.class).getOne(id)).withSelfRel());
				 
			 }
		 }
		 
		 
		 return ResponseEntity.ok(enderecos);
	}
	
	
	
	@GetMapping("/endereco/{id}")
	public ResponseEntity<Object> getOne(@PathVariable @Valid UUID id){
		var endereco = enderecoRepository.findById(id);
		
		if(endereco.isEmpty()) {
			return notFound();
		}
		var enderecoObj  = endereco.get();
		
		enderecoObj.add(linkTo(methodOn(EnderecoController.class).getAll()).withRel("Enderecos List"));
		
		return ResponseEntity.status(HttpStatus.OK).body(enderecoObj);
		
	}
	
	
	
	
	
	
	public ResponseEntity<Object> notFound(){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
	}
	
}
