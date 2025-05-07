package com.sistema.pizzaria.models;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class ProductModel 	extends RepresentationModel<ProductModel> implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private UUID id;
	private String name;
	private String description;
	private Double price;
	public ProductModel(String name, String description, Double price) {
		
	
		this.name = name;
		this.description = description;
		this.price = price;
	}
	
	public ProductModel() {
		super();
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductModel other = (ProductModel) obj;
		return Objects.equals(id, other.id);
	}	
	
	
	
	
	
}
