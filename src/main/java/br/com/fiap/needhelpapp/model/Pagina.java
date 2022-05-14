package br.com.fiap.needhelpapp.model;

import java.util.ArrayList;
import java.util.Collection;

import br.com.fiap.dao.UsuarioDAO;
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
	 * O usuário criador da página.
	 * Como usuários podem ser excluídos mas suas páginas criadas
	 * devem permanecer, o ID original e autor são extraídos e salvos
	 * no banco. O objeto por si, não possui relação, mas pode ser procurado
	 * e atribuído através do ID salvo na criação da página.
	 */
	@Transient
	private Usuario Autor;
	
	/**
	 * ID do usuário autor da página
	 */
	@Column(name = "PG_IDAUTOR")
	private int idAutor;
	
	/**
	 * Nome do usuário autor da página
	 */
	@Column(name = "PG_NOMEAUTOR", length = 50)
	private String nomeAutor;
	
	/**
	 * Procedimentos relacionados a pagina
	 */
	@OneToMany(mappedBy = "pagina")
	private Collection<Procedimento> procedimentos;

	/**
	 * Recursos relacionados a pagina
	 */
	@OneToMany(mappedBy = "pagina")
	private Collection<Recursos> recursos;
	
	/**
	 * Favoritos de usuários na página
	 */
	@OneToMany(mappedBy = "pagina")
	private Collection<Favorito> favoritos;
	
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
	 * @return o usuário autor
	 */
	public Usuario getAutor(EntityManager em) {
		if(Autor != null) {
			return Autor;
		}
		else {
			int searchId = this.getIdAutor();
			UsuarioDAO usuarioDAO = new UsuarioDAO(em);
			Usuario searchedUser = usuarioDAO.recuperar(searchId);
			if(searchedUser != null) {
				return searchedUser; 
			}
			else {
				//TODO: Para efeitos de um MVP e simplicidade,
				//ao invés de retornar uma exception, retornamos um null
				
				//throw new Exception("Usuário não encontrado");
				return null;
			}
		}
	}

	/**
	 * Usado para setar o autor.
	 * @param autor o usuário autor a ser atribuido
	 */
	public void setAutor(Usuario autor) {
		Autor = autor;
		this.setIdAutor(autor.getId());
		this.setNomeAutor(autor.getLogin());
	}

	/**
	 * @return o ID do usuário autor
	 */
	public int getIdAutor() {
		return idAutor;
	}

	/**
	 * Classe que não deve ser utilizada externamente. Para
	 * setar informações de autor, deve ser utilizado o método
	 * SetAutor().
	 * @param idAutor to ID do usuário autor a ser atribuido
	 */
	private void setIdAutor(int idAutor) {
		this.idAutor = idAutor;
	}

	/**
	 * @return o nome do autor
	 */
	public String getNomeAutor() {
		return nomeAutor;
	}

	/**
	 * Classe que não deve ser utilizada externamente. Para
	 * setar informações de autor, deve ser utilizado o método
	 * SetAutor().
	 * @param nomeAutor o nome do autor a ser atribuido
	 */
	private void setNomeAutor(String nomeAutor) {
		this.nomeAutor = nomeAutor;
	}

	/**
	 * @param nome o nome a atribuir
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return os procedimentos
	 */
	public Collection<Procedimento> getProcedimentos() {
		if(procedimentos != null) {
			return procedimentos;
		}
		else {
			return new ArrayList<Procedimento>();
		}		
	}

	/**
	 * @param procedimentos os procedimentos a serem atribuidos
	 */
	public void setProcedimentos(Collection<Procedimento> procedimentos) {
		this.procedimentos = procedimentos;
	}

	/**
	 * @return os recursos
	 */
	public Collection<Recursos> getRecursos() {
		if(recursos != null) {
			return recursos;
		}
		else {
			return new ArrayList<Recursos>();
		}
	}

	/**
	 * @param recursos os recursos a serem atribuidos
	 */
	public void setRecursos(Collection<Recursos> recursos) {
		this.recursos = recursos;
	}

	/**
	 * @return os favoritos
	 */
	public Collection<Favorito> getFavoritos() {
		if(favoritos != null) {
			return favoritos;
		}
		else {
			return new ArrayList<Favorito>();
		}
	}

	/**
	 * @param favoritos os favoritos a serem atribuidos
	 */
	public void setFavoritos(Collection<Favorito> favoritos) {
		this.favoritos = favoritos;
	}

	/**
	 * @return o id e o nome formatados juntos em formato string
	 */
	@Override
	public String toString() {
		return id + " - " + nome;
	}
}
