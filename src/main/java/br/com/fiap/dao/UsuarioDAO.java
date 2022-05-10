package br.com.fiap.dao;

import java.util.List;
import br.com.fiap.needhelpapp.model.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;

/**
 * Classe DAO para persistência de objetos Usuario
 */
public class UsuarioDAO extends GenericDAO<Usuario, Integer> {
	
	/**
	 * Construtor
	 * @param entityManager Entity Manager
	 */
	public UsuarioDAO(EntityManager entityManager) {
		super(entityManager);
	}
	
	/**
	 * Pesquisa de Usuario por login
	 * @param login String com o login desejado para a pesquisa
	 * @return uma Lista de usuarios com o login desejado
	 */
	@SuppressWarnings("unchecked")
	public List<Usuario> getByLogin(String login) 
	{		 
		return (List<Usuario>)this.em.createQuery(
				"select e from Usuario e where e.login = :login"
				).setParameter("login", login)
				.getResultList();
	}
	
	/**
	 * Pesquisa de Usuario por um login único
	 * @param login String com o login desejado para a pesquisa
	 * @return Um único resultado com o login desejado
	 */
	public Usuario getByLoginUnique(String login) 
	{
		try {
			return (Usuario)this.em.createQuery(
					"select e from Usuario e where e.login = :login"
					).setParameter("login", login)
					.getSingleResult();			
		}
		catch(NonUniqueResultException ex) {
			//throw new NonUniqueResultException(ex.getMessage());
			System.out.println("A pesquisa retornou mais de um resultado para o parâmetro desejado: " + ex.getMessage());
			return null;
		}
		catch(NoResultException ex) {
			//throw new NoResultException(ex.getMessage());
			System.out.println("Não foram encontrados resultados para o parâmetro desejado - " + ex.getMessage());
			return null;
		}
	}
	
	/**
	 * Pesquisa de Usuario por um login parcial
	 * @param login String com o login (parcial) desejado para a pesquisa
	 * @return uma Lista com os usuarios cujos logins possuem a string desejada
	 */
	@SuppressWarnings("unchecked")
	public List<Usuario> getByLoginPartial(String login) 
	{		 
		return (List<Usuario>)this.em.createQuery(
				"select e from Usuario e where e.login like concat('%', :login,'%')"
				).setParameter("login", login)
				.getResultList();
	}
	
	/**
	 * Exclui um Usuario garantindo a exclusão também de seus Favoritos.
	 * @param chave Chave (ID) do banco de um registro.
	 */
	public void excluir(Usuario usuario) {
		FavoritoDAO favoritoDAO = new FavoritoDAO(this.em);
		favoritoDAO.excluirByUsuarioId(usuario);
		
		Integer chave = usuario.getId();
		super.excluir(chave);
	}
}
