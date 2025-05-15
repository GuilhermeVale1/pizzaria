package com.sistema.pizzaria.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.pizzaria.dtos.FuncionarioRecordDto;
import com.sistema.pizzaria.enums.UserRole;
import com.sistema.pizzaria.models.FuncionarioModel;
import com.sistema.pizzaria.repositories.FuncionarioRepository;

@RestController
public class FuncionarioController {

    private final AuthenticationManager authenticationManager;
	
	private FuncionarioRepository funcionarioRepository;

    FuncionarioController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

	@PostMapping("/funcionario")
	public ResponseEntity<Object> saveFuncionario(FuncionarioRecordDto funcionarioDto){
		
		if(funcionarioRepository.findByEmail(funcionarioDto.email()) != null) {
			return notFound();
		}
		String encryptedPassword = new BCryptPasswordEncoder().encode(funcionarioDto.password());
		var funcionarioModel = new FuncionarioModel(funcionarioDto.cpf(), funcionarioDto.nome() , funcionarioDto.email(), funcionarioDto.telefone(), encryptedPassword);
		funcionarioModel.setRole(UserRole.ADMIN);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioRepository.save(funcionarioModel));
	}
	
	@GetMapping("/funcionario")
	public ResponseEntity<Object> getAll(){
		
		var clientes = funcionarioRepository.findAll();
		
		return ResponseEntity.status(HttpStatus.OK).body(clientes);
		
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
