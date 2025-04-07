package com.sistema.pizzaria.controllers;

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

import com.sistema.pizzaria.dtos.PedidoRecordDto;
import com.sistema.pizzaria.models.ClienteModel;
import com.sistema.pizzaria.models.PedidoModel;
import com.sistema.pizzaria.repositories.ClienteRepository;
import com.sistema.pizzaria.repositories.PedidoRepository;

import jakarta.validation.Valid;

@RestController
public class PedidoController {

    private final ClienteController clienteController;
	
	@Autowired
	PedidoRepository pedidoRepository;
	@Autowired
	ClienteRepository clienteRepository;


    PedidoController(ClienteController clienteController) {
        this.clienteController = clienteController;
    }
	
	
	@PostMapping("/pedidos")
	public ResponseEntity<Object> create(@RequestBody @Valid PedidoRecordDto pedidoDto){
		
		var pedidoModel = new PedidoModel();
		BeanUtils.copyProperties(pedidoDto, pedidoModel);
		
		Optional<ClienteModel> cliente = clienteRepository.findById(pedidoDto.id());
		
		if(cliente.isEmpty()) {
			return notFound();
		}
		
		var clienteGet = cliente.get();
		
		pedidoModel.setClienteModel(clienteGet);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(pedidoRepository.save(pedidoModel));
		
	}
	

	
	@GetMapping("/pedidos")
	public ResponseEntity<List<PedidoModel>> getAll(){
		
		List<PedidoModel> pedidos = pedidoRepository.findAll();
		
		return ResponseEntity.status(HttpStatus.OK).body(pedidos);
		
	}
	
	@GetMapping("/pedidos/{id}")
	public ResponseEntity<Object> getOne(@PathVariable(value = "id")  UUID id){
		
		var pedido = pedidoRepository.findById(id);
		if(pedido.isEmpty()) {
			return notFound();
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(pedido);
		
	}
	
//	@PutMapping("/pedidos")
//	public ResponseEntity<Object> update(@RequestBody @Valid UUID id , ){
//		
//	}
	
	
	@DeleteMapping("/pedidos/{id}")
	
	public ResponseEntity<Object> delete (@PathVariable(value = "id") UUID id) {
		Optional<PedidoModel>  pedido = pedidoRepository.findById(id);
		if(pedido.isEmpty()) {
			return notFound();
		}
		var pedidoGet = pedido.get(); 
		
		pedidoRepository.delete(pedidoGet);
		
		return ResponseEntity.status(HttpStatus.OK).body("Pedido deletado com sucesso");
		
	}
	
	
	public ResponseEntity<Object> notFound(){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
	}
	
	
}
