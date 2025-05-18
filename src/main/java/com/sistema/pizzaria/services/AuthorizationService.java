package com.sistema.pizzaria.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sistema.pizzaria.repositories.ClienteRepository;
import com.sistema.pizzaria.repositories.FuncionarioRepository;


@Service
public class AuthorizationService implements UserDetailsService {
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	FuncionarioRepository funcionarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		  	var cliente = clienteRepository.findByEmail(username);
		  	if (cliente != null) {
		  		return cliente;
	        }
		  	
		  	var funcionario = funcionarioRepository.findByEmail(username);
	        if (funcionario != null) {
	            return funcionario;
	        }
	        throw new UsernameNotFoundException("Usuário não encontrado com email: " + username);
	}

}
