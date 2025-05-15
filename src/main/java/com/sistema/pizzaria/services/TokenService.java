package com.sistema.pizzaria.services;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sistema.pizzaria.models.ClienteModel;
import com.sistema.pizzaria.models.FuncionarioModel;

@Service
public class TokenService {

   
	
	@Value("${api.security.token.secret}")
	private String secret;

   
	public String generateToken(ClienteModel client) {
		
	
		
		
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			String token = JWT.create()
					.withIssuer("auth-api")
					.withSubject(client.getEmail())
					.withExpiresAt(getExpirationDate())
					.sign(algorithm);
			
			return token;
		}catch(JWTCreationException exception) {
			throw new RuntimeException("Erro generation token" , exception);
		}
		
		
	}
	
	public String generateToken(FuncionarioModel funcionarioModel) {
		
	
		
		
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			String token = JWT.create()
					.withIssuer("auth-api")
					.withSubject(funcionarioModel.getEmail())
					.withExpiresAt(getExpirationDate())
					.sign(algorithm);
			
			return token;
		}catch(JWTCreationException exception) {
			throw new RuntimeException("Erro generation token" , exception);
		}
		
		
	}
	
	
	
	public String validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm)
					.withIssuer("auth-api")
					.build()
					.verify(token)
					.getSubject();
		}catch(JWTVerificationException exception) {
			return "";
		}
		
	}
	
	
	public String generateRecoveryToken(ClienteModel cliente) {
		
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			String token = JWT.create()
					.withIssuer("password-recovery")
					.withSubject(cliente.getEmail())
					.withExpiresAt(getRecoverExpiration())
					.sign(algorithm);
			
			return token;
					
			
		}catch (JWTCreationException exception) {
	        throw new RuntimeException("Erro ao gerar token de recuperação", exception);
	    }
		
	}
	
	
	public String validateRecoveryToken(String token) {
	    try {
	        Algorithm algorithm = Algorithm.HMAC256(secret);
	        return JWT.require(algorithm)
	                .withIssuer("password-recovery")
	                .build()
	                .verify(token)
	                .getSubject();
	    } catch (JWTVerificationException exception) {
	        return null;
	    }
	}
	
	
	
	private Instant getRecoverExpiration() {
		return LocalDateTime.now().plusMinutes(15).toInstant(ZoneOffset.of("-03:00"));
	}
	
	private Instant getExpirationDate() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
	
	
	
	

	
}
