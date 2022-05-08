package br.com.fiap.dao;

import java.util.List;
import br.com.fiap.needhelpapp.model.Categoria;
import br.com.fiap.needhelpapp.model.Pagina;
import jakarta.persistence.EntityManager;

/**
 * Classe DAO para persistência de objetos Pagina
 */
public class PaginaDAO extends GenericDAO<Pagina, Integer> {
	
	/**
	 * Construtor
	 * @param entityManager Entity Manager
	 */
	public PaginaDAO(EntityManager entityManager) {
		super(entityManager);
	}

	/**
	 * Pesquisa de Pagina por nome
	 * @param nome String com o nome desejado para a pesquisa
	 * @return uma Lista de paginas com o nome desejado
	 */
	@SuppressWarnings("unchecked")
	public List<Pagina> getByName(String nome) 
	{		 
		return (List<Pagina>)this.em.createQuery(
				"select e from Pagina e where e.nome = :nome"
				).setParameter("nome", nome)
				.getResultList();
	}
	
	/**
	 * Pesquisa de Pagina por um nome único
	 * @param nome String com o nome desejado para a pesquisa
	 * @return Um único resultado com o nome desejado
	 */
	public Pagina getByNameUnique(String nome) 
	{		 
		return (Pagina)this.em.createQuery(
				"select e from Pagina e where e.nome = :nome"
				).setParameter("nome", nome)
				.getSingleResult();
	}
	
	/**
	 * Pesquisa de Pagina por um nome parcial
	 * @param nome String com o nome (parcial) desejado para a pesquisa
	 * @return uma Lista com as paginas cujos nomes possuem a string desejada
	 */
	@SuppressWarnings("unchecked")
	public List<Pagina> getByNamePartial(String nome) 
	{		 
		return (List<Pagina>)this.em.createQuery(
				"select e from Pagina e where e.nome like concat('%', :nome,'%')"
				).setParameter("nome", nome)
				.getResultList();
	}
	
	/**
	 * Pesquisa de Pagina de acordo com uma categoria
	 * @param categoria Objeto Categoria
	 * @return Lista de paginas relacionadas a uma categoria
	 */
	@SuppressWarnings("unchecked")
	public List<Pagina> getByCategoria(Categoria categoria) 
	{		 
		int categoriaId = categoria.getId();
		return (List<Pagina>)this.em.createQuery(
				"select e from Pagina e where e.categoria = :categoriaId"
				).setParameter("categoriaId", categoriaId)
				.getResultList();
	}
	
	/**
	 * Pesquisa de Pagina de acordo com uma categoria
	 * @param categoriaId ID de uma categoria
	 * @return Lista de paginas relacionadas a uma categoria
	 */
	@SuppressWarnings("unchecked")
	public List<Pagina> getByCategoria(Integer categoriaId) 
	{		 
		return (List<Pagina>)this.em.createQuery(
				"select e from Pagina e where e.categoria = :categoriaId"
				).setParameter("categoriaId", categoriaId)
				.getResultList();
	}
	
}
