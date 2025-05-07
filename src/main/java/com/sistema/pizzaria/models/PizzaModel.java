package com.sistema.pizzaria.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name= "tb_pizzas")
public class PizzaModel extends ProductModel{
	
	private static final long serialVersionUID = 1L;
	
	private String tamanho;
	private String ingredientes;
	
	
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
