package com.sistema.pizzaria.controllers;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.pizzaria.dtos.PedidosProductsRecordDto;
import com.sistema.pizzaria.models.BebidasModel;
import com.sistema.pizzaria.models.PedidoProductsModel;
import com.sistema.pizzaria.models.PizzaModel;
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
	public ResponseEntity<Object> create(@RequestBody @Valid PedidosProductsRecordDto pedidosDto) {
		var pedidoOpt = pedidoRepository.findById(pedidosDto.idPedido());
		if (pedidoOpt.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido não encontrado.");
		}

		
		Optional<PizzaModel> pizzaOpt = Optional.empty();
		if (pedidosDto.idPizza() != null) {
			pizzaOpt = pizzaRepository.findById(pedidosDto.idPizza());
		}

		
		Optional<BebidasModel> bebidaOpt = Optional.empty();
		if (pedidosDto.idBebida() != null) {
			bebidaOpt = bebidaRepository.findById(pedidosDto.idBebida());
		}

		
		if (pizzaOpt.isPresent() || bebidaOpt.isPresent()) {
			var pedidoProductsModel = new PedidoProductsModel();
			BeanUtils.copyProperties(pedidosDto, pedidoProductsModel);
			pedidosProductsRepository.save(pedidoProductsModel);

			return ResponseEntity.status(HttpStatus.CREATED).body("Pedido feito com sucesso.");
		}

		return ResponseEntity.badRequest().body("Nenhum produto válido foi informado.");

	}
	
	
	public ResponseEntity<Object> notFound(){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
	}
	
}
