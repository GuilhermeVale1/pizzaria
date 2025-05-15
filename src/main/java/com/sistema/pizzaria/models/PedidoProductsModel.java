package com.sistema.pizzaria.models;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_pedidospr")
public class PedidoProductsModel {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name = "pedido_id" )
	private PedidoModel pedidoModel;
	
	@ManyToOne
	@JoinColumn(name = "pizza_id")
	private PizzaModel pizzaModel;
	
	@ManyToOne
	@JoinColumn(name = "bebida_id")
	private BebidasModel bebidasModel;
	
	
	
	public PedidoProductsModel() {
		
		// TODO Auto-generated constructor stub
	}

	public PedidoModel getPedidoModel() {
		return pedidoModel;
	}

	public void setPedidoModel(PedidoModel pedidoModel) {
		this.pedidoModel = pedidoModel;
	}

	public PizzaModel getPizzaModel() {
		return pizzaModel;
	}

	public void setPizzaModel(PizzaModel pizzaModel) {
		this.pizzaModel = pizzaModel;
	}

	public BebidasModel getBebidasModel() {
		return bebidasModel;
	}

	public void setBebidasModel(BebidasModel bebidasModel) {
		this.bebidasModel = bebidasModel;
	}
	
	
	
	
	
	
}
