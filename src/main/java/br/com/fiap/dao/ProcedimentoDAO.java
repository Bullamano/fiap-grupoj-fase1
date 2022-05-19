package br.com.fiap.dao;

import java.util.List;
import br.com.fiap.utils.Enums;
import jakarta.persistence.*;
import br.com.fiap.needhelpapp.model.Pagina;
import br.com.fiap.needhelpapp.model.Procedimento;

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
		try {
			return (Procedimento)this.em.createQuery(
					"select e from Procedimento e where e.titulo = :titulo"
					).setParameter("titulo", titulo)
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
	 * Pesquisa de Procedimento por um nome parcial
	 * @param titulo String com o titulo (parcial) desejado para a pesquisa
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
	 * @param ordem Se as informações devem ser retornadas em ordem ascendente, decrescente ou irrelevante (conforme está no banco)
	 * @return Lista de procedimentos pertencentes a uma Pagina
	 */
	@SuppressWarnings("unchecked")
	public List<Procedimento> getByPagina(Pagina pagina,  Enums.order ordem) 
	{
		if(ordem == Enums.order.asc) {
    		return (List<Procedimento>)this.em.createQuery(
    				"select e from Procedimento e where e.pagina = :pagina order by e.ordem asc"
    				).setParameter("pagina", pagina)
    				.getResultList();
    	}
    	else if(ordem == Enums.order.desc) {
    		return (List<Procedimento>)this.em.createQuery(
    				"select e from Procedimento e where e.pagina = :pagina order by e.ordem desc"
    				).setParameter("pagina", pagina)
    				.getResultList();
    	}
    	else {
    		return (List<Procedimento>)this.em.createQuery(
    				"select e from Procedimento e where e.pagina = :pagina"
    				).setParameter("pagina", pagina)
    				.getResultList();
    	}
	}
	
	/**
	 * Pesquisa de Procedimento de acordo com uma Pagina
	 * @param paginaId paginaId ID de uma Pagina
	 * @param ordem Se as informações devem ser retornadas em ordem ascendente, decrescente ou irrelevante (conforme está no banco)
	 * @return Lista de procedimentos pertencentes a uma Pagina
	 */
	public List<Procedimento> getByPagina(Integer paginaId, Enums.order ordem) 
	{
    	Pagina pagina = new PaginaDAO(this.em).recuperar(paginaId);
    	
    	return getByPagina(pagina, ordem);
	}
	
}
