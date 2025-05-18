package com.sistema.pizzaria.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.pizzaria.dtos.LoginRecordDto;
import com.sistema.pizzaria.dtos.LoginResponseDto;
import com.sistema.pizzaria.models.ClienteModel;
import com.sistema.pizzaria.models.FuncionarioModel;
import com.sistema.pizzaria.services.TokenService;

import jakarta.validation.Valid;

@RestController
public class LoginController {
	
	  @Autowired
	  private AuthenticationManager authenticationManager;

	  @Autowired
	  private TokenService tokenService;
	
	
	@PostMapping("/login")
	 public ResponseEntity login(@RequestBody @Valid LoginRecordDto loginDto) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(
                loginDto.email(), loginDto.password()
            );

            Authentication auth = authenticationManager.authenticate(usernamePassword);

         
            var token = auth.getPrincipal() instanceof ClienteModel cliente
                ? tokenService.generateToken(cliente)
                : tokenService.generateToken((FuncionarioModel) auth.getPrincipal());

            return ResponseEntity.ok(new LoginResponseDto(token));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Credenciais inválidas.");
        }
    }
	
}
