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
 * Recursos presentes em uma pagina (links)
 *
 */
@Entity
@Table(name="nhaf_recursos")
public class Recursos {
	
	/**
	 * ID do banco de dados dos recursos de uma pagina
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "REC_ID")
	private Integer id;
	
	/**
	 * Pagina a qual os recursos pertencem
	 */
	@JoinColumn(name="REC_PG_ID")
	@ManyToOne
	private Pagina pagina;
	
	/**
	 * Ordem na qual os recursos devem aparecer
	 */
	@Column(name="REC_ORDEM")
	private Integer ordem;
	
	/**
	 * Link para um video relacionado ao assunto
	 */
	@Column(name = "REC_LINKVIDEO", length = 200)
	private String linkVideo;
	
	/**
	 * Link para uma leitura relacionado ao assunto
	 */
	@Column(name = "REC_LINKLEITURA", length = 200)
	private String linkLeitura;
	
	/**
	 * Link para imagens relacionadas ao assunto
	 */
	@Column(name = "REC_LINKIMAGEM", length = 200)
	private String linkImagem;

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
	 * @return String - o linkVideo
	 */
	public String getLinkVideo() {
		return linkVideo;
	}

	/**
	 * @param linkVideo o linkVideo a ser atribuido
	 */
	public void setLinkVideo(String linkVideo) {
		this.linkVideo = linkVideo;
	}

	/**
	 * @return String - o linkLeitura
	 */
	public String getLinkLeitura() {
		return linkLeitura;
	}

	/**
	 * @param linkLeitura o linkLeitura a ser atribuido
	 */
	public void setLinkLeitura(String linkLeitura) {
		this.linkLeitura = linkLeitura;
	}

	/**
	 * @return String - o linkImagem
	 */
	public String getLinkImagem() {
		return linkImagem;
	}

	/**
	 * @param linkImagem o linkImagem a ser atribuido
	 */
	public void setLinkImagem(String linkImagem) {
		this.linkImagem = linkImagem;
	}

	/**
	 * @return String - o id e o nome formatados juntos em formato string
	 */
	@Override
	public String toString() {
		
		String stringToReturn = id.toString() + " - P�gina: " + pagina.getNome();
		
		if(linkVideo != null && !(linkVideo.isEmpty() || linkVideo.isBlank())) {
			stringToReturn = stringToReturn + " - V�deo: " + linkVideo;
		}
		
		if(linkLeitura != null && !(linkLeitura.isEmpty() || linkLeitura.isBlank())) {
			stringToReturn = stringToReturn + " - Leitura: " + linkLeitura;
		}
		
		if(linkImagem != null && !(linkImagem.isEmpty() || linkImagem.isBlank())) {
			stringToReturn = stringToReturn + " - Imagem: " + linkImagem;
		}
		
		return stringToReturn;
	}
}
