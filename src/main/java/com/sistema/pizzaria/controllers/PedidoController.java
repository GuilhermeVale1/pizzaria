package com.sistema.pizzaria.controllers;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.pizzaria.dtos.PedidoRecordDto;
import com.sistema.pizzaria.models.PedidoModel;
import com.sistema.pizzaria.repositories.PedidoRepository;

import jakarta.validation.Valid;

@RestController
public class PedidoController {
	
	@Autowired
	PedidoRepository pedidoRepository;
	
	
	@PostMapping("/pedidos")
	public ResponseEntity<PedidoModel> create(@RequestBody @Valid PedidoRecordDto pedidoDto){
		
		var pedidoModel = new PedidoModel();
		BeanUtils.copyProperties(pedidoDto, pedidoModel);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(pedidoRepository.save(pedidoModel));
		
	}
	
	
	@GetMapping("/pedidos")
	public ResponseEntity<List<PedidoModel>> getAll(){
		
		List<PedidoModel> pedidos = pedidoRepository.findAll();
		
		return ResponseEntity.status(HttpStatus.OK).body(pedidos);
		
	}
	
}
