package com.sistema.pizzaria.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name= "tb_pizzas")
public class PizzaModel extends ProductModel{
	
	private static final long serialVersionUID = 1L;
	
	private String tamanho;
	private String ingredientes;
	
	
	@OneToMany(mappedBy = "pizzaModel", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<PedidoProductsModel> pedidosProdutoModels;
	
	
	public PizzaModel(String name, String description, Double price, String tamanho, String ingredientes) {
        super(name, description, price); 
        this.tamanho = tamanho;
        this.ingredientes = ingredientes;
    }
	
	
	
	public PizzaModel() {
		
	}



	public String getTamanho() {
		return tamanho;
	}
	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}
	public String getIngredientes() {
		return ingredientes;
	}
	public void setIngredientes(String ingredientes) {
		this.ingredientes = ingredientes;
	}
	
	
}
