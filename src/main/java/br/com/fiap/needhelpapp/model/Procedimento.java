package br.com.fiap.needhelpapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Procedimentos presentes em uma pagina (subtitulos/secções)
 *
 */
@Entity
@Table(name="nhaf_procedimentos")
public class Procedimento {

	/**
	 * ID do banco de dados do procedimento
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PRC_ID")
	private Integer id;
	
	/**
	 * Pagina a qual o procedimento pertence
	 */
	@JoinColumn(name="PRC_PG_ID")
	@ManyToOne
	private Pagina pagina;
	
	/**
	 * Ordem na qual os procedimentos devem aparecer
	 */
	@Column(name = "PRC_ORDEM")
	private Integer ordem;

	/**
	 * Titulo do procedimento (subtitulos da pagina)
	 */
	@Column(name = "PRC_TITULO", length = 50)
	private String titulo;
	
	/**
	 * Tarefas presentes no procedimento
	 */
	@Column(name = "PRC_TAREFAS", length = 200)
	private String tarefas;

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
	 * @return Integer - a ordem
	 */
	public Integer getOrdem() {
		return ordem;
	}

	/**
	 * @param ordem a ordem a ser atribuida
	 */
	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	/**
	 * @return String - o titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo o titulo a ser atribuido
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return String - as tarefas
	 */
	public String getTarefas() {
		return tarefas;
	}

	/**
	 * @param tarefas as tarefas a serem atribuidas
	 */
	public void setTarefas(String tarefas) {
		this.tarefas = tarefas;
	}
	
	/**
	 * @return String - o id e o nome formatados juntos em formato string
	 */
	@Override
	public String toString() {
		return id + " - " + titulo + " - " + tarefas;
	}
}
