package com.sistema.pizzaria.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.pizzaria.dtos.EnderecoRecordDto;
import com.sistema.pizzaria.dtos.PedidoResponseDto;
import com.sistema.pizzaria.models.ClienteModel;
import com.sistema.pizzaria.models.PedidoModel;
import com.sistema.pizzaria.repositories.ClienteRepository;
import com.sistema.pizzaria.repositories.EnderecoRepository;
import com.sistema.pizzaria.repositories.PedidoRepository;

@RestController
public class PedidoController {

	private final ClienteController clienteController;

	@Autowired
	PedidoRepository pedidoRepository;
	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	EnderecoRepository enderecoRepository;

	PedidoController(ClienteController clienteController) {
		this.clienteController = clienteController;
	}

	@PostMapping("/pedidos")
	public ResponseEntity<Object> create() {

		var authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !(authentication.getPrincipal() instanceof ClienteModel clienteModel)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado.");
		}

		var enderecoOpt = enderecoRepository.findByClienteModelAndPrincipal(clienteModel, true);
		if (enderecoOpt.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Escolha um endereço ou cadastre um.");
		}

		var endereco = enderecoOpt.get();

		var pedidoModel = new PedidoModel(clienteModel);
		pedidoModel.setEnderecoModel(endereco);
		

		var pedidoSalvo = pedidoRepository.save(pedidoModel);


		
		
		EnderecoRecordDto enderecoDto = new EnderecoRecordDto(endereco.getCep(), endereco.getBairro(),
				endereco.getRua(), endereco.getNumero(), endereco.getComplemento());

		PedidoResponseDto responseDto = new PedidoResponseDto(pedidoSalvo.getUuid(), pedidoSalvo.getAtendido(),
				enderecoDto);

		return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);

	}

	@PutMapping("/pedido/{id}")
	public ResponseEntity<Object> atendido(@PathVariable(name = "id") UUID id) {

		var pedido = pedidoRepository.findById(id);
		if (pedido.isEmpty()) {
			return notFound();
		}

		PedidoModel pedidoModel = pedido.get();
		pedidoModel.setAtendido(true);

		pedidoRepository.save(pedidoModel);

		return ResponseEntity.status(HttpStatus.OK).body("pedido atendido");

	}

	@GetMapping("/pedidos")
	public ResponseEntity<List<PedidoModel>> getAll() {

		List<PedidoModel> pedidos = pedidoRepository.findAll();

		return ResponseEntity.status(HttpStatus.OK).body(pedidos);

	}

	@GetMapping("/pedidos/{id}")
	public ResponseEntity<Object> getOne(@PathVariable(value = "id") UUID id) {

		var pedido = pedidoRepository.findById(id);
		if (pedido.isEmpty()) {
			return notFound();
		}

		return ResponseEntity.status(HttpStatus.OK).body(pedido);

	}

//	@PutMapping("/pedidos")
//	public ResponseEntity<Object> update(@RequestBody @Valid UUID id , ){
//		
//	}

	@DeleteMapping("/pedidos/{id}")

	public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID id) {
		Optional<PedidoModel> pedido = pedidoRepository.findById(id);
		if (pedido.isEmpty()) {
			return notFound();
		}
		var pedidoGet = pedido.get();

		pedidoRepository.delete(pedidoGet);

		return ResponseEntity.status(HttpStatus.OK).body("Pedido deletado com sucesso");

	}

	public ResponseEntity<Object> notFound() {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
	}

}
