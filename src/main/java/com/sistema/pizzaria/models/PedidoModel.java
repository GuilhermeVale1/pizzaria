package com.sistema.pizzaria.models;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_pedidos")
public class PedidoModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID uuid;
	
	@ManyToOne
	@JoinColumn(name = "client_id")
	@JsonIgnoreProperties("enderecos")
	private ClienteModel clienteModel;
	
	
	private Boolean atendido;
	
	@ManyToOne
	@JoinColumn(name = "endereco_id")
	private EnderecoModel enderecoModel;
	

	@OneToMany(mappedBy = "pedidoModel", cascade = CascadeType.ALL , orphanRemoval = true)
	private List<PedidoProductsModel> produtosPedidoModel;
	

	public PedidoModel(ClienteModel cliente) {
		
		this.clienteModel = cliente;
		atendido = false;
	}
	
	public PedidoModel() {
		
	}
	
	
	public EnderecoModel getEnderecoModel() {
		return enderecoModel;
	}

	public void setEnderecoModel(EnderecoModel enderecoModel) {
		this.enderecoModel = enderecoModel;
	}


	public Boolean getAtendido() {
		return atendido;
	}

	public void setAtendido(Boolean atendido) {
		this.atendido = atendido;
	}

	@Override
	public int hashCode() {
		return Objects.hash(uuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PedidoModel other = (PedidoModel) obj;
		return Objects.equals(uuid, other.uuid);
	}
	
	public List<PedidoProductsModel> getProdutosPedidoModel() {
	    return produtosPedidoModel;
	}
	
	
	public void setProdutosPedidoModel(List<PedidoProductsModel> produtosPedidoModel) {
	    this.produtosPedidoModel = produtosPedidoModel;
	}
	

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}



	public ClienteModel getClienteModel() {
		return clienteModel;
	}

	public void setClienteModel(ClienteModel clienteModel) {
		this.clienteModel = clienteModel;
	}
}
