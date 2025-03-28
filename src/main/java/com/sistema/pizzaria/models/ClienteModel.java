package com.sistema.pizzaria.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_clientes")
public class ClienteModel extends PessoaModel {
	
	
	@OneToMany(mappedBy = "clienteModel")
	private List<PedidoModel> pedidos;
	
	
	public List<PedidoModel> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<PedidoModel> pedidos) {
		this.pedidos = pedidos;
	}

	public ClienteModel(String cpf, String nome, String telefone, String endereco) {
		super(cpf, nome, telefone, endereco);
	}
	
	public ClienteModel() {
		
	}
	

	
	
}
