package com.sistema.pizzaria.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_clientes")
public class ClienteModel extends PessoaModel {
	
	//@OneToMany(mappedBy = "cliente")
	//private PedidoModel pedido;

	public ClienteModel(String cpf, String nome, String telefone, String endereco) {
		super(cpf, nome, telefone, endereco);
	}
	
	public ClienteModel() {
		
	}
	

	
	
}
