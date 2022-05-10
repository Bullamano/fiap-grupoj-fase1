package br.com.fiap.dao;

import java.util.List;
import br.com.fiap.needhelpapp.model.Favorito;
import br.com.fiap.needhelpapp.model.Pagina;
import br.com.fiap.needhelpapp.model.Usuario;
import jakarta.persistence.EntityManager;

/**
 * Classe DAO para persistência de objetos Favorito
 */
public class FavoritoDAO extends GenericDAO<Favorito, Integer> {
	
	/**
	 * Construtor
	 * @param entityManager Entity Manager
	 */
	public FavoritoDAO(EntityManager entityManager) {
		super(entityManager);
	}
	
	/**
	 * Pesquisa Favoritos por um determinado Usuario atrelado
	 * @param usuario Usuario atrelado ao(s) Favorito(s)
	 * @return Uma lista de Favoritos atrelados ao Usuario
	 */
	@SuppressWarnings("unchecked")
	public List<Favorito> getByUsuario(Usuario usuario) 
	{
		return (List<Favorito>)this.em.createQuery(
				"select e from Favorito e where e.usuario = :usuario"
				).setParameter("usuario", usuario)
				.getResultList();
	}
	
	/**
	 * Pesquisa Favoritos por uma determinada Pagina atrelada
	 * @param pagina Pagina atrelada ao(s) Favorito(s)
	 * @return Uma lista de Favoritos atrelados a Pagina
	 */
	@SuppressWarnings("unchecked")
	public List<Favorito> getByPagina(Pagina pagina) 
	{
		return (List<Favorito>)this.em.createQuery(
				"select e from Favorito e where e.pagina = :pagina"
				).setParameter("pagina", pagina)
				.getResultList();
	}
}
