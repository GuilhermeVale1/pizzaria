package com.sistema.pizzaria.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_bebidas")
public class BebidasModel extends ProductModel{
	
	private static final long serialVersionUID = 1L;
	
	
	
	private Integer volume;
	private String tipo;
	
	
	public BebidasModel(String name, String description, Double price, Integer volume, String tipo) {
        super(name, description, price); 
        this.volume = volume;
        this.tipo = tipo;
    }


	public BebidasModel() {
	}


	public Integer getVolume() {
		return volume;
	}


	public void setVolume(Integer volume) {
		this.volume = volume;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
	
	
}
