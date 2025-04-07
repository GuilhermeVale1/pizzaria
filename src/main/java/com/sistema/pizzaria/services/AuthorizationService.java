package com.sistema.pizzaria.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sistema.pizzaria.repositories.ClienteRepository;


@Service
public class AuthorizationService implements UserDetailsService {
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return clienteRepository.findByEmail(username);
	}

}
