package com.sistema.pizzaria.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.pizzaria.dtos.PizzaRecordDto;
import com.sistema.pizzaria.models.PizzaModel;
import com.sistema.pizzaria.repositories.PizzaRepository;

import jakarta.validation.Valid;

@RestController
public class PizzaController {
	
	
	@Autowired
	PizzaRepository pizzaRepository;
	
	@PostMapping("/pizzas")
	public ResponseEntity<PizzaModel> savePizza(@RequestBody @Valid PizzaRecordDto pizzaRecordDto ){
		
		var pizzaModel = new PizzaModel();
		BeanUtils.copyProperties(pizzaRecordDto, pizzaModel);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(pizzaRepository.save(pizzaModel));
		
	}
	
	@GetMapping("/pizzas")
	public ResponseEntity<List<PizzaModel>> getAllPizzas(){
		return ResponseEntity.status(HttpStatus.OK).body(pizzaRepository.findAll());
	}
	
	
	@GetMapping("/pizzas/{id}")
	public ResponseEntity<Object> getOnePizza(@PathVariable(value="id") UUID id){
		
		Optional<PizzaModel> pizza = pizzaRepository.findById(id);
		
		if(pizza.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
			
			
		}
		return ResponseEntity.status(HttpStatus.OK).body(pizza.get());
		
	}
	
}
