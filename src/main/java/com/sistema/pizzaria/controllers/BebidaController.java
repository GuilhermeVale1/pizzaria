package com.sistema.pizzaria.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.pizzaria.dtos.BebidaRecordDto;
import com.sistema.pizzaria.models.BebidasModel;
import com.sistema.pizzaria.repositories.BebidaRepository;

import jakarta.validation.Valid;

@RestController

public class BebidaController {

    
	@Autowired
	BebidaRepository bebidaRepository;

   
	@PostMapping("/bebidas")
	public ResponseEntity<BebidasModel> saveBebida(@RequestBody @Valid BebidaRecordDto bebidaDto){
		var bebidaModel = new BebidasModel();
		BeanUtils.copyProperties(bebidaDto, bebidaModel);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(bebidaRepository.save(bebidaModel));
	}
	
	@GetMapping("/bebidas")
	
	public ResponseEntity<List<BebidasModel>> getAll(){
		List<BebidasModel> bebidas = bebidaRepository.findAll();
		
		if(!bebidas.isEmpty()) {
			for( BebidasModel bebida : bebidas ) {
				UUID id = bebida.getId();
				bebida.add(linkTo(methodOn(BebidaController.class).getOne(id)).withRel("Bebidas List"));
			}
			
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(bebidas);
		
		
		
	}
	
	@GetMapping("/bebidas/{id}")
	public ResponseEntity<Object> getOne(@PathVariable(value = "id") UUID id) {
		
		Optional <BebidasModel> bebida = bebidaRepository.findById(id);
		if (bebida.isEmpty()) {
			return notFound();
		}
		
		bebida.get().add(linkTo(methodOn(BebidaController.class).getAll()).withRel("Bebidas List"));
		
		return ResponseEntity.status(HttpStatus.OK).body(bebida.get());
		
	}
	
	@PutMapping("/bebidas/{id}")
	public ResponseEntity<Object> updateBebida(@RequestBody @Valid BebidaRecordDto bebidaRecordDto , @PathVariable(value = "id") UUID id ){
		Optional<BebidasModel> bebida = bebidaRepository.findById(id);
		if(bebida.isEmpty()) {
			return notFound();
		}
		var bebidaGet = bebida.get();
		
		BeanUtils.copyProperties(bebidaRecordDto, bebidaGet);
		
		
		
		return ResponseEntity.status(HttpStatus.OK).body(bebidaRepository.save(bebidaGet));
		
	}
	
	@DeleteMapping("/bebidas/{id}")
	public ResponseEntity<Object> deleteBebida(@PathVariable(value = "id") UUID id){
		
		Optional<BebidasModel> bebida = bebidaRepository.findById(id);
		if(bebida.isEmpty()) {
			return notFound();
		}
		
		bebidaRepository.delete(bebida.get());
		
		return ResponseEntity.status(HttpStatus.OK).body("Bebida deletada com sucesso");
		
		
	}
	
	public ResponseEntity<Object> notFound(){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
	}
	
	   
	   
}
