package com.sistema.pizzaria.models;

import java.util.Collection;
import java.util.List;



import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_clientes")
public class ClienteModel extends PessoaModel  {
	
	
	@OneToMany(mappedBy = "clienteModel")
	@JsonIgnore
	private List<PedidoModel> pedidos;
	
	
	public List<PedidoModel> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<PedidoModel> pedidos) {
		this.pedidos = pedidos;
	}

	public ClienteModel(String cpf, String nome, String email, String telefone, String password) {
		super(cpf, nome, email, telefone, password);
	}
	
	public ClienteModel() {
		
	}

	
	
	
}
