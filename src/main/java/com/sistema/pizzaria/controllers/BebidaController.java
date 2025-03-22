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

import com.sistema.pizzaria.dtos.BebidaRecordDto;
import com.sistema.pizzaria.models.BebidasModel;
import com.sistema.pizzaria.repositories.BebidaRepository;
import com.sistema.pizzaria.repositories.PizzaRepository;
import jakarta.validation.Valid;

@RestController

public class BebidaController {

    private final PizzaRepository pizzaRepository;
	@Autowired
	BebidaRepository bebidaRepository;

    BebidaController(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }
	
	@PostMapping("/bebidas")
	public ResponseEntity<BebidasModel> saveBebida(@RequestBody @Valid BebidaRecordDto bebidaDto){
		var bebidaModel = new BebidasModel();
		BeanUtils.copyProperties(bebidaDto, bebidaModel);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(bebidaRepository.save(bebidaModel));
	}
	
	@GetMapping("/bebidas")
	
	public ResponseEntity<List<BebidasModel>> getAll(){
		List<BebidasModel> bebidas = bebidaRepository.findAll();
		
		return ResponseEntity.status(HttpStatus.OK).body(bebidas);
	}
	
	@GetMapping("/bebidas/{id}")
	public ResponseEntity<Object> getOne(@PathVariable(value = "id") UUID id) {
		
		Optional <BebidasModel> bebida = bebidaRepository.findById(id);
		if (bebida.isEmpty()) {
			return notFound();
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(bebida.get());
		
	}
	
	public ResponseEntity<Object> notFound(){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
	}
	
	   
	   
}
