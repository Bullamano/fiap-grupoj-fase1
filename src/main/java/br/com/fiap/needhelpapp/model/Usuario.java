package br.com.fiap.needhelpapp.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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
	 * Email do usu�rio
	 */
	@Column(name="USR_EMAIL", length = 50)
	private String email;
	
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
	 * @return o email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email o email a ser atribu�do
	 */
	public void setEmail(String email) {
		this.email = email;
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
		//TODO adicionar BCrypt ou algo semelhante. SHA-256 � suficiente apenas para um MVP 
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(senha.getBytes(StandardCharsets.UTF_8));
			String senhaHash = new String(Hex.encode(hash));
			this.senha = senhaHash;
		} 
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			
			//TODO : Inseguro. Apenas aqui para um MVP
			//Correto seria soltar uma exce��o
			this.senha = senha;
		}
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
		return id + " - " + login;
	}
	
	/**
	 * M�todo para verificar a senha de um usu�rio
	 * @param Senha a senha a ser testada
	 * @return Um booleano indicando se a senha est� correta ou n�o
	 */
	public Boolean testSenha(String senha) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(senha.getBytes(StandardCharsets.UTF_8));
			String senhaHash = new String(Hex.encode(hash));
						
			return (senhaHash.equals(this.senha));
		} 
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			
			//TODO : Incorreto. Apenas aqui para um MVP
			//Correto seria soltar uma exce��o
			return false;
		}
	}	
}
