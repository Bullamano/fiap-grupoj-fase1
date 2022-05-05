package br.com.fiap.needhelpapp.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;

import org.bouncycastle.util.encoders.Hex;

import jakarta.persistence.*;

/**
 * Usu�rios da aplica��o 
 *
 */
@Entity
@Table(name="nhaf_usuarios")
public class Usuario {
	
	/**
	 * ID do banco de dados do usu�rio
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USR_ID")
	private Integer id;
	
	/**
	 * Login do usu�rio
	 */
	@Column(name = "USR_LOGIN", length = 50)
	private String login;
	
	/**
	 * Hash da senha do usu�rio
	 */
	@Column(name = "USR_SENHA", length = 200)
	private String senha;
		
	/**
	 * Favoritos do usu�rio
	 */
	@OneToMany(mappedBy = "usuario")
	private Collection<Favorito> favoritos;

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
	 * @return o login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login o login a ser atribuido
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return a senha
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * @param senha a senha a ser atribuida
	 */
	public void setSenha(String senha) {
		
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(senha.getBytes(StandardCharsets.UTF_8));
			String senhaHash = new String(Hex.encode(hash));
			this.senha = senhaHash;
		} 
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			
			//TODO : Inseguro. Apenas aqui para um MVP
			this.senha = senha;
		}
	}

	/**
	 * @return os favoritos
	 */
	public Collection<Favorito> getFavoritos() {
		return favoritos;
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
		return id + " - " + login;
	}
	
}