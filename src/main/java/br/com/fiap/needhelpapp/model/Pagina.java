package br.com.fiap.needhelpapp.model;

import jakarta.persistence.*;

/**
 * Pagina da aplicação presente em uma categoria
 *
 */
@Entity
@Table(name="nhaf_paginas")
public class Pagina {

	/**
	 * ID do banco de dados da pagina
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PG_ID")
	private Integer id;
	
	/**
	 * Categoria a qual a pagina pertence
	 */
	@JoinColumn(name="PG_CTG_ID")
	@ManyToOne
	private Categoria categoria;
	
	/**
	 * Nome da pagina
	 */
	@Column(name = "PG_NOME", length = 50)
	private String nome;

	/**
	 * @return o id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id o id a atribuir
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return a categoria
	 */
	public Categoria getCategoria() {
		return categoria;
	}

	/**
	 * @param categoria a categoria a atribuir
	 */
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	/**
	 * @return o nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome o nome a atribuir
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	
}
