package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "user_name")
	private String name;
	
	@Column(name = "user_senha")
	private String senha;
	
	@Enumerated(EnumType.STRING)
	private PasswordType tipoSenha;
	
	public User() {
		
	}

	public User(String name, String senha, PasswordType tipoSenha) {
		this.name = name;
		this.senha = senha;
		this.tipoSenha = tipoSenha;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public PasswordType getTipoSenha() {
		return tipoSenha;
	}

	public void setTipoSenha(PasswordType tipoSenha) {
		this.tipoSenha = tipoSenha;
	}

}