package com.sistema.pizzaria.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.sistema.pizzaria.models.ClienteModel;
import com.sistema.pizzaria.repositories.ClienteRepository;

@Service
public class RecoverAccountService{

    private final AuthenticationManager authenticationManager;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private TokenService tokenService;
	
	@Value("${api.security.token.secret}")
	private String secret;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	
	

    RecoverAccountService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    
    
	
	
	public void processForgotPassword(String email) {
		
		UserDetails user = clienteRepository.findByEmail(email);
		
		if(user == null) {
			throw new RuntimeException("Email não encontrado");
		}
		
		ClienteModel cliente = (ClienteModel) user;
		
		String token = tokenService.generateRecoveryToken(cliente);
		
		sendRecovery(cliente.getEmail(), token);
		
		
		
		
	}
	
	
	private void sendRecovery(String to, String token) {
		
		String subject = "Recuperação de senha - pizzaria ";
		String resetLink =  token;
		String message = "Olá!\n\nCopie o código kabaixo para redefinir sua senha:\n" + resetLink + "\n\nSe você não solicitou isso, ignore este e-mail.";
		
		SimpleMailMessage email = new SimpleMailMessage();
		
		email.setTo(to);
		email.setSubject(subject);
		email.setText(message);
		
		mailSender.send(email);
		
	}
	
	public String isResetTokenValid(String token) {
		
		String email = tokenService.validateRecoveryToken(token);
	    return email;
		
		
		
	}



}
