package com.sistema.pizzaria.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.pizzaria.dtos.FuncionarioRecordDto;
import com.sistema.pizzaria.dtos.FuncionarioUpdateRecordDto;
import com.sistema.pizzaria.enums.UserRole;
import com.sistema.pizzaria.models.FuncionarioModel;
import com.sistema.pizzaria.repositories.FuncionarioRepository;
import com.sistema.pizzaria.services.TokenService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@RestController
public class FuncionarioController {
	@Autowired
    private AuthenticationManager authenticationManager;
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	@Autowired
	private TokenService tokenService;
	

 

	@PostMapping("/funcionario")
	public ResponseEntity<Object> saveFuncionario(@RequestBody FuncionarioRecordDto funcionarioDto){
		
		if(funcionarioRepository.findByEmail(funcionarioDto.email()) != null) {
			return notFound();
		}
		String encryptedPassword = new BCryptPasswordEncoder().encode(funcionarioDto.password());
		var funcionarioModel = new FuncionarioModel(funcionarioDto.cpf(), funcionarioDto.nome() , funcionarioDto.email(), funcionarioDto.telefone(), encryptedPassword);
		funcionarioModel.setRole(UserRole.ADMIN);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioRepository.save(funcionarioModel));
	}
	
	@GetMapping("/funcionario")
	public ResponseEntity<List<FuncionarioModel>> getAll(){
		List<FuncionarioModel> funcionarioModels =  funcionarioRepository.findAll();
		
		
		if(!funcionarioModels.isEmpty()) {
			for(FuncionarioModel funcionario: funcionarioModels) {
				UUID id = funcionario.getId();
				funcionario.add(linkTo(methodOn(FuncionarioController.class).getOneFuncionario(id)).withSelfRel());
			}
		}
		
		
		return ResponseEntity.status(HttpStatus.OK).body(funcionarioModels);
		
	}
	
	@GetMapping("/funcionario/{id}")
	public ResponseEntity<Object> getOneFuncionario(@PathVariable UUID id){
		Optional <FuncionarioModel> funcionarioModel = funcionarioRepository.findById(id);
		
		if(funcionarioModel.isEmpty()) {
			return notFound();
		}
		
		funcionarioModel.get().add(linkTo(methodOn(FuncionarioController.class).getAll()).withRel("Funcionario list"));
		
		
		return ResponseEntity.status(HttpStatus.OK).body(funcionarioModel.get());
		
		
	}
	
	@PutMapping("funcionario/{id}")
	public ResponseEntity<Object> putFuncionario(@PathVariable UUID id, @RequestBody FuncionarioUpdateRecordDto funcionarioDto) {
		
		
		var funcionarioModel = funcionarioRepository.findById(id);
		
		if(funcionarioModel.isEmpty()) {
			return notFound();
		}
		var func = funcionarioModel.get();
		
	    if (funcionarioDto.nome() != null) {
	        func.setNome(funcionarioDto.nome());
	    }
	    if (funcionarioDto.email() != null) {
	        func.setEmail(funcionarioDto.email());
	    }
	    if (funcionarioDto.telefone() != null) {
	        func.setTelefone(funcionarioDto.telefone());
	    }
	    if (funcionarioDto.password() != null) {
	    	String encryptedPassword = new BCryptPasswordEncoder().encode(funcionarioDto.password());
	        func.setPassword(encryptedPassword);
	    }
	    
	    funcionarioRepository.save(func);
	    
	    return ResponseEntity.ok("Funcionário atualizado com sucesso");
	    
	    
	}
	
	@DeleteMapping("/funcionario/{id}")
	public ResponseEntity<Object> deletaFuncionario( @PathVariable(value = "id" ) UUID id ){
		
		 Optional<FuncionarioModel> funcionario = funcionarioRepository.findById(id);
		    
		 if (funcionario.isEmpty()) {
		     return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário não encontrado");
		 }
		
		 funcionarioRepository.delete(funcionario.get());
		 return ResponseEntity.status(HttpStatus.OK).body("Funcionário deletado com sucesso");
		
	}
	
	
	public ResponseEntity<Object> notFound(){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
	}
	

}
