package com.sistema.pizzaria.models;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_enderecos")

public class EnderecoModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name = "client_id")
	private ClienteModel clienteModel;

	
	private String cep;
	private String bairro;
	private String rua;
	private String numero;
	private String complemento;
	
	
	public EnderecoModel(String cep, String bairro, String rua, String numero, String complemento) {
		super();
		this.cep = cep;
		this.bairro = bairro;
		this.rua = rua;
		this.numero = numero;
		this.complemento = complemento;
	}


	public EnderecoModel() {
		super();
		// TODO Auto-generated constructor stub
	}


	public void setCep(String cep) {
		this.cep = cep;
	}


	public void setBairro(String bairro) {
		this.bairro = bairro;
	}


	public void setRua(String rua) {
		this.rua = rua;
	}


	public void setNumero(String numero) {
		this.numero = numero;
	}


	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}


	public UUID getId() {
		return id;
	}


	public void setId(UUID id) {
		this.id = id;
	}


	public String getCep() {
		return cep;
	}


	public String getBairro() {
		return bairro;
	}


	public String getRua() {
		return rua;
	}


	public String getNumero() {
		return numero;
	}


	public String getComplemento() {
		return complemento;
	}
	
	
	
	
	
	
	
}
