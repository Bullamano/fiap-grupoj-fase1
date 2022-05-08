package br.com.fiap.dao;

import java.util.List;
import br.com.fiap.needhelpapp.model.Categoria;
import jakarta.persistence.EntityManager;

/**
 * Classe DAO para persistência de objetos Categoria
 */
public class CategoriaDAO extends GenericDAO<Categoria, Integer> {
	
	/**
	 * Construtor
	 * @param entityManager Entity Manager
	 */
	public CategoriaDAO(EntityManager entityManager) {
		super(entityManager);
	}
	
	/**
	 * Pesquisa de Categoria por nome
	 * @param nome String com o nome desejado para a pesquisa
	 * @return uma Lista de categorias com o nome desejado
	 */
	@SuppressWarnings("unchecked")
	public List<Categoria> getByName(String nome) 
	{		 
		return (List<Categoria>)this.em.createQuery(
				"select e from Categoria e where e.nome = :nome"
				).setParameter("nome", nome)
				.getResultList();
	}
	
	/**
	 * Pesquisa de Categoria por um nome único
	 * @param nome String com o nome desejado para a pesquisa
	 * @return Um único resultado com o nome desejado
	 */
	public Categoria getByNameUnique(String nome) 
	{		 
		return (Categoria)this.em.createQuery(
				"select e from Categoria e where e.nome = :nome"
				).setParameter("nome", nome)
				.getSingleResult();
	}
	
	/**
	 * Pesquisa de Categoria por um nome parcial
	 * @param nome String com o nome (parcial) desejado para a pesquisa
	 * @return uma Lista com as categorias cujos nomes possuem a string desejada
	 */
	@SuppressWarnings("unchecked")
	public List<Categoria> getByNamePartial(String nome) 
	{		 
		return (List<Categoria>)this.em.createQuery(
				"select e from Categoria e where e.nome like concat('%', :nome,'%')"
				).setParameter("nome", nome)
				.getResultList();
	}
}
