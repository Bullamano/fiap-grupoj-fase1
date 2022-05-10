package br.com.fiap.needhelpapp.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Categoria de páginas da aplicação
 */
@Entity
@Table(name="nhaf_categorias")
public class Categoria {
	
	/**
	 * ID do banco de dados da categoria
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CTG_ID")
	private Integer id;
	
	/**
	 * Nome da categoria
	 */
	@Column(name = "CTG_NOME", length = 50)
	private String nome;
	
	/**
	 * Paginas que estao relacionadas a uma categoria
	 */
	@OneToMany(mappedBy = "categoria")
	private Collection<Pagina> paginas;

	/**
	 * @return o id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id o id a ser atribuido
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return o nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome o nome a ser atribuido
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	/**
	 * @return as paginas dessa categoria
	 */
	public Collection<Pagina> getPaginas() {
		if(paginas != null) {
			return paginas;
		}
		else {
			return new ArrayList<Pagina>();
		}
	}

	/**
	 * @param paginas as paginas a serem atribuidas
	 */
	public void setPaginas(Collection<Pagina> paginas) {
		this.paginas = paginas;
	}
	
	/**
	 * @return o id e o nome formatados juntos em formato string
	 */
	@Override
	public String toString() {
		return id + " - " + nome;
	}
	
}
