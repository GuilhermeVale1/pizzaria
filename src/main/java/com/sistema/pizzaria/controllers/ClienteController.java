package com.sistema.pizzaria.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.pizzaria.dtos.ClienteRecordDto;
import com.sistema.pizzaria.dtos.LoginRecordDto;
import com.sistema.pizzaria.dtos.LoginResponseDto;
import com.sistema.pizzaria.enums.UserRole;
import com.sistema.pizzaria.models.ClienteModel;
import com.sistema.pizzaria.repositories.ClienteRepository;
import com.sistema.pizzaria.services.TokenService;

import jakarta.validation.Valid;



@RestController
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired 
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping("/clientes")
	public ResponseEntity<Object> saveCliente( @RequestBody @Valid ClienteRecordDto clientDto ){
		
		if(clienteRepository.findByEmail(clientDto.email()) != null ){
			
			return notFound();
			
		}
		
		
		String encryptedPassword = new BCryptPasswordEncoder().encode(clientDto.password());
		
		var clienteModel = new ClienteModel(clientDto.cpf(), clientDto.nome() , clientDto.email(), clientDto.telefone(), encryptedPassword);
		
		clienteModel.setRole(UserRole.USER);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteRepository.save(clienteModel));
		
	}
	
	

	
	
	@GetMapping("/clientes")
	public ResponseEntity<List<ClienteModel>> getAll(){
		 List<ClienteModel> clientes = clienteRepository.findAll();
		 
		 if(!clientes.isEmpty()) {
			 for(ClienteModel cliente : clientes) {
				 UUID id = cliente.getId();
				 
				 cliente.add(linkTo(methodOn(ClienteController.class).getOne(id)).withSelfRel());
				 
				 
			 }
		 }
		 
		
		 
		 return ResponseEntity.status(HttpStatus.OK).body(clientes);
	}
	
	@GetMapping("/clientes/{id}")
	public ResponseEntity<Object> getOne(@PathVariable(value = "id") UUID id){
		
		Optional<ClienteModel> cliente = clienteRepository.findById(id);
		
		if(cliente.isEmpty()) {
			return notFound();
		}
		
		cliente.get().add(linkTo(methodOn(ClienteController.class).getAll()).withRel("Client list"));
		return ResponseEntity.status(HttpStatus.OK).body(cliente.get());
		
	}
	
	@PutMapping("/clientes/{id}")
	
	public ResponseEntity<Object> update(@PathVariable(value = "id") UUID id, @RequestBody @Valid ClienteRecordDto clientDto){
		
		Optional<ClienteModel> cliente = clienteRepository.findById(id);
		
		
		
		if(cliente.isEmpty()) {
			return notFound();
		}
		
		var clienteGet = cliente.get();
		
		BeanUtils.copyProperties(clientDto, clienteGet);
		
		return ResponseEntity.status(HttpStatus.OK).body(clienteRepository.save(clienteGet));
	}
	
	
	@DeleteMapping("/clientes/{id}") 
	public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID id){
		
		Optional<ClienteModel> cliente = clienteRepository.findById(id);
		if(cliente.isEmpty()) {
			return notFound();
		}
		
		var clienteGet = cliente.get();
		
		clienteRepository.delete(clienteGet);
		
		return ResponseEntity.status(HttpStatus.OK).body("Cliente deletado com sucesso");
		
		
		
	}
	public ResponseEntity<Object> notFound(){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
	}
	
}
