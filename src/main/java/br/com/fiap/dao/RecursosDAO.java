package br.com.fiap.dao;

import java.util.ArrayList;
import java.util.List;
import br.com.fiap.needhelpapp.model.Pagina;
import br.com.fiap.needhelpapp.model.Recursos;
import br.com.fiap.utils.Enums;
import jakarta.persistence.EntityManager;

public class RecursosDAO extends GenericDAO<Recursos, Integer> {
	
	public RecursosDAO(EntityManager entityManager) {
		super(entityManager);
	}
	
	/**
	 * Pesquisa de Recursos de acordo com uma Pagina
	 * @param pagina Objeto Pagina
	 * @param ordem Se as informações devem ser retornadas em ordem ascendente, decrescente ou irrelevante (conforme está no banco)
	 * @return Lista de recursos pertencentes a uma Pagina
	 */
	@SuppressWarnings("unchecked")
	public List<Recursos> getByPagina(Pagina pagina,  Enums.order ordem) 
	{
		if(ordem == Enums.order.asc) {
    		return (List<Recursos>)this.em.createQuery(
    				"select e from Recursos e where e.pagina = :pagina order by e.ordem asc"
    				).setParameter("pagina", pagina)
    				.getResultList();
    	}
    	else if(ordem == Enums.order.desc) {
    		return (List<Recursos>)this.em.createQuery(
    				"select e from Recursos e where e.pagina = :pagina order by e.ordem desc"
    				).setParameter("pagina", pagina)
    				.getResultList();
    	}
    	else {
    		return (List<Recursos>)this.em.createQuery(
    				"select e from Recursos e where e.pagina = :pagina"
    				).setParameter("pagina", pagina)
    				.getResultList();
    	}
	}
	
	/**
	 * Pesquisa de Recursos de acordo com uma Pagina
	 * @param entityManager Entity Manager
	 * @param paginaId paginaId ID de uma Pagina
	 * @param ordem Se as informações devem ser retornadas em ordem ascendente, decrescente ou irrelevante (conforme está no banco)
	 * @return Lista de recursos pertencentes a uma Pagina
	 */
	public List<Recursos> getByPagina(EntityManager entityManager, Integer paginaId, Enums.order ordem) 
	{
    	Pagina pagina = new PaginaDAO(entityManager).recuperar(paginaId);
    	
    	return getByPagina(pagina, ordem);
	}
	
	/**
	 * Pesquisa de Recursos por link
	 * @param link String com o link desejado para a pesquisa
	 * @param linkType Tipo do link a ser pesquisado (presente nos Enums)
	 * @return uma Lista de recursos com o link desejado
	 */
	@SuppressWarnings("unchecked")
	public List<Recursos> getByLink(String link, Enums.linkType linkType) 
	{		 
		switch(linkType) {		
		case imagem:
			return (List<Recursos>)this.em.createQuery(
					"select e from Recursos e where e.linkImagem = :linkImagem"
					).setParameter("linkImagem", link)
					.getResultList();
		
		case video:
			return (List<Recursos>)this.em.createQuery(
					"select e from Recursos e where e.linkVideo = :linkVideo"
					).setParameter("linkVideo", link)
					.getResultList();
			
		case leitura:
			return (List<Recursos>)this.em.createQuery(
					"select e from Recursos e where e.linkLeitura = :linkLeitura"
					).setParameter("linkLeitura", link)
					.getResultList();
			
		default:
			//throw new IllegalArgumentException("O argumento linkType não foi preenchido corretamente.");
			System.out.println("O argumento linkType não foi preenchido corretamente.");
			List<Recursos> listaVazia = new ArrayList<Recursos>();
			return listaVazia;
		}
	}
		
	/**
	 * Pesquisa de Recursos por um nome parcial
	 * @param link String com o link (parcial) desejado para a pesquisa
	 * @param linkType Tipo do link a ser pesquisado (presente nos Enums)
	 * @return Uma Lista com as procedimentos cujos titulos possuem a string desejada
	 */
	@SuppressWarnings("unchecked")
	public List<Recursos> getByLinkPartial(String link, Enums.linkType linkType) 
	{
		
		switch(linkType) {		
		case imagem:
			return (List<Recursos>)this.em.createQuery(
					"select e from Recursos e where e.linkImagem like concat('%', :linkImagem,'%')"
					).setParameter("linkImagem", link)
					.getResultList();
		
		case video:
			return (List<Recursos>)this.em.createQuery(
					"select e from Recursos e where e.linkVideo like concat('%', :linkVideo,'%')"
					).setParameter("linkVideo", link)
					.getResultList();
			
		case leitura:
			return (List<Recursos>)this.em.createQuery(
					"select e from Recursos e where e.linkLeitura like concat('%', :linkLeitura,'%')"
					).setParameter("linkLeitura", link)
					.getResultList();
			
		default:
			//throw new IllegalArgumentException("O argumento linkType não foi preenchido corretamente.");
			System.out.println("O argumento linkType não foi preenchido corretamente.");
			List<Recursos> listaVazia = new ArrayList<Recursos>();
			return listaVazia;
		}
	}
	
}
