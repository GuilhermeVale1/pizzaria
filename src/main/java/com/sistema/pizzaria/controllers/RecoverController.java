package com.sistema.pizzaria.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.pizzaria.dtos.ForgotPasswordDTO;
import com.sistema.pizzaria.dtos.ResetPasswordDto;
import com.sistema.pizzaria.models.ClienteModel;
import com.sistema.pizzaria.repositories.ClienteRepository;
import com.sistema.pizzaria.services.RecoverAccountService;

import jakarta.validation.Valid;

@RestController
public class RecoverController {

    private final ClienteController clienteController;

    private final ClienteRepository clienteRepository;

    @Autowired
    private RecoverAccountService recoverAccountService;

    RecoverController(ClienteRepository clienteRepository, ClienteController clienteController) {
        this.clienteRepository = clienteRepository;
        this.clienteController = clienteController;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody @Valid ForgotPasswordDTO forgotPasswordDTO) {
        recoverAccountService.processForgotPassword(forgotPasswordDTO.email());

        
        return ResponseEntity.ok("Se o e-mail existir, um link de recuperação será enviado." );
    }
    
    @PutMapping("reset-password")
    public ResponseEntity<Object> recoverPassword(@RequestParam("token") String token , @RequestBody ResetPasswordDto passwordDto ){
    	
    	String email = recoverAccountService.isResetTokenValid(token);
    	
    	if (email == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido ou expirado.");
        }
		
    	ClienteModel cliente = (ClienteModel) clienteRepository.findByEmail(email);
    	
    	  if (cliente == null) {
    	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
    	        
    	   }
    	  
    	  String senha = new BCryptPasswordEncoder().encode(passwordDto.password());
    	  
    	  cliente.setPassword(senha);
    	  clienteRepository.save(cliente);
    	  
    	  return ResponseEntity.ok("Senha redefinida com sucesso!");
    	
    	
    }
    
}
