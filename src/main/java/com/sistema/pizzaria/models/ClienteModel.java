package com.sistema.pizzaria.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_clientes")
public class ClienteModel extends PessoaModel  {
	
	
	@OneToMany(mappedBy = "clienteModel")
	@JsonIgnore
	private List<PedidoModel> pedidos;
	
	
	@OneToMany(mappedBy = "clienteModel", cascade = CascadeType.ALL  , orphanRemoval = true )
	@JsonIgnore
	private List<EnderecoModel> enderecos;
	
	
	
	public List<PedidoModel> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<PedidoModel> pedidos) {
		this.pedidos = pedidos;
	}
	
	public List<EnderecoModel> getEnderecoModels(){
		return enderecos;
	}
	
	public void setEnderecos(List<EnderecoModel> enderecos) {
		this.enderecos = enderecos;
	}
	
	

	public ClienteModel(String cpf, String nome, String email, String telefone, String password) {
		super(cpf, nome, email, telefone, password);
	}
	
	public ClienteModel() {
		
	}

	
	
	
}
