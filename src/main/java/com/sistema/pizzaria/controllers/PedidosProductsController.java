package com.sistema.pizzaria.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.pizzaria.dtos.PedidosProductsRecordDto;
import com.sistema.pizzaria.models.PedidoProductsModel;
import com.sistema.pizzaria.repositories.BebidaRepository;
import com.sistema.pizzaria.repositories.PedidoRepository;
import com.sistema.pizzaria.repositories.PedidosProductsRepository;
import com.sistema.pizzaria.repositories.PizzaRepository;

import jakarta.validation.Valid;

@RestController
public class PedidosProductsController {
	private final PedidoController pedidoController;
	
	@Autowired 
	PedidoRepository pedidoRepository;
	
	@Autowired
	PedidosProductsRepository pedidosProductsRepository;
	
	@Autowired 
	PizzaRepository pizzaRepository;
	
	@Autowired
	BebidaRepository bebidaRepository;
	
	
	
	public PedidosProductsController(PedidoController pedidoController) {
        this.pedidoController = pedidoController;
    }
	
	@PostMapping("/products")
	public ResponseEntity<Object> create(@RequestBody @Valid PedidosProductsRecordDto pedidosDto){
		  var pedidoOpt = pedidoRepository.findById(pedidosDto.idPedido());
		  var pizzaOpt = pizzaRepository.findById(pedidosDto.idPizza());
		  var bebidaOpt = bebidaRepository.findById(pedidosDto.idBebida());
		
		
		if((pedidoOpt.isPresent() && (pizzaOpt.isPresent() || bebidaOpt.isPresent())) ) {
			var pedidoProductsModel = new PedidoProductsModel();
			BeanUtils.copyProperties(pedidosDto, pedidoProductsModel);
			
			pedidosProductsRepository.save(pedidoProductsModel);
			
			return ResponseEntity.status(HttpStatus.CREATED).body("pedido feito com sucesso");
		}
		
		return notFound();
		
	}
	
	
	public ResponseEntity<Object> notFound(){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
	}
	
}
