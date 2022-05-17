package br.com.fiap.needhelpapp.model;

import jakarta.persistence.*;

/**
 * Registro de página favorita de um usuário
 *
 */
@Entity
@Table(name="nhaf_favoritos")
public class Favorito {
	
	/**
	 * ID do banco de dados do favorito
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FAV_ID")
	private Integer id;
	
	/**
	 * A que usuário o favorito pertence
	 */
	@JoinColumn(name="FAV_USR_ID")
	@ManyToOne
	private Usuario usuario;
	
	/**
	 * A qual página o favorito se refere
	 */
	@JoinColumn(name="FAV_PG_ID")
	@ManyToOne
	private Pagina pagina;

	/**
	 * @return Integer - o id
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
	 * @return Usuario - o usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario o usuario a ser atribuido
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return Pagina - a pagina
	 */
	public Pagina getPagina() {
		return pagina;
	}

	/**
	 * @param pagina a pagina a ser atribuida
	 */
	public void setPagina(Pagina pagina) {
		this.pagina = pagina;
	}
	
	/**
	 * @return String - o id e o nome formatados juntos em formato string
	 */
	@Override
	public String toString() {
		return id + " - Favorito do usuário: " + usuario + " - Página: " + pagina;
	}
	
}
