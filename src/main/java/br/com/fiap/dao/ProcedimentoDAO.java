package br.com.fiap.dao;

import java.util.List;
import br.com.fiap.needhelpapp.model.Pagina;
import br.com.fiap.needhelpapp.model.Procedimento;
import jakarta.persistence.EntityManager;

/**
 * Classe DAO para persistência de objetos Procedimento
 */
public class ProcedimentoDAO extends GenericDAO<Procedimento, Integer> {
	
	/**
	 * Construtor
	 * @param entityManager Entity manager
	 */
	public ProcedimentoDAO(EntityManager entityManager) {
		super(entityManager);
	}

	/**
	 * Pesquisa de Procedimento por nome
	 * @param titulo String com o titulo desejado para a pesquisa
	 * @return uma Lista de procedimentos com o titulo desejado
	 */
	@SuppressWarnings("unchecked")
	public List<Procedimento> getByTitulo(String titulo) 
	{		 
		return (List<Procedimento>)this.em.createQuery(
				"select e from Procedimento e where e.titulo = :titulo"
				).setParameter("titulo", titulo)
				.getResultList();
	}
	
	/**
	 * Pesquisa de Procedimento por um nome único
	 * @param titulo String com o titulo desejado para a pesquisa
	 * @return Um único resultado com o titulo desejado
	 */
	public Procedimento getByTituloUnique(String titulo) 
	{		 
		return (Procedimento)this.em.createQuery(
				"select e from Procedimento e where e.titulo = :titulo"
				).setParameter("titulo", titulo)
				.getSingleResult();
	}
	
	/**
	 * Pesquisa de Procedimento por um nome parcial
	 * @param nome String com o titulo (parcial) desejado para a pesquisa
	 * @return uma Lista com as procedimentos cujos titulos possuem a string desejada
	 */
	@SuppressWarnings("unchecked")
	public List<Procedimento> getByTituloPartial(String titulo) 
	{		 
		return (List<Procedimento>)this.em.createQuery(
				"select e from Procedimento e where e.titulo like concat('%', :titulo,'%')"
				).setParameter("titulo", titulo)
				.getResultList();
	}
	
	/**
	 * Pesquisa de Procedimento de acordo com uma Pagina
	 * @param pagina Objeto Pagina
	 * @return Lista de procedimentos pertencentes a uma Pagina
	 */
	@SuppressWarnings("unchecked")
	public List<Procedimento> getByPagina(Pagina pagina) 
	{
		return (List<Procedimento>)this.em.createQuery(
				"select e from Procedimento e where e.pagina = :pagina"
				).setParameter("pagina", pagina)
				.getResultList();
	}
	
	/**
	 * Pesquisa de Procedimento de acordo com uma Pagina
	 * @param paginaId ID de uma Pagina
	 * @return Lista de procedimentos pertencentes a uma Pagina
	 */
	@SuppressWarnings("unchecked")
	public List<Procedimento> getByPagina(EntityManager entityManager,Integer paginaId) 
	{
    	Pagina pagina = new PaginaDAO(entityManager).recuperar(paginaId);
		
		return (List<Procedimento>)this.em.createQuery(
				"select e from Procedimento e where e.pagina = :pagina"
				).setParameter("pagina", pagina)
				.getResultList();
	}
	
}
