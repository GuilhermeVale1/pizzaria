package com.sistema.pizzaria.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sistema.pizzaria.repositories.ClienteRepository;
import com.sistema.pizzaria.services.TokenService;


@Configuration
@EnableWebSecurity

public class SecurityConfiguration {

    
	
	@Autowired
	SecurityFilter securityFilter;
	
 

    @Bean 
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.POST, "/clientes", "/clientes/login" , "/forgot-password" ).permitAll()
                .requestMatchers(HttpMethod.PUT, "/reset-password").permitAll()
                .requestMatchers(HttpMethod.GET, "/clientes").hasRole("USER")
                .requestMatchers(HttpMethod.DELETE, "//clientes/{id}").hasRole("USER")
                .requestMatchers(HttpMethod.PUT, "/clientes/{id}").hasRole("USER")
                .requestMatchers(HttpMethod.POST, "/pizzas", "/bebidas").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }
   
    
 
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    	
    	return authenticationConfiguration.getAuthenticationManager();
    	
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
    
}
