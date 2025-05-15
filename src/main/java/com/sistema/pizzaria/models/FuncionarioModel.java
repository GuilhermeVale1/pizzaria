package com.sistema.pizzaria.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_funcionarios")

public class FuncionarioModel extends PessoaModel {
	
	public FuncionarioModel(String cpf, String nome, String email, String telefone, String password) {
		super(cpf, nome, email, telefone, password);
	}

	public FuncionarioModel() {
		
	}
	
	
	

}
