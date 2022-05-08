package br.com.fiap.dao;

import java.util.List;
import br.com.fiap.needhelpapp.model.Categoria;
import br.com.fiap.needhelpapp.model.Pagina;
import jakarta.persistence.*;

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
		try {
			return (Pagina)this.em.createQuery(
					"select e from Pagina e where e.nome = :nome"
					).setParameter("nome", nome)
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
		return (List<Pagina>)this.em.createQuery(
				"select e from Pagina e where e.categoria = :categoria"
				).setParameter("categoria", categoria)
				.getResultList();
	}
	
	/**
	 * Pesquisa de Pagina de acordo com uma categoria
	 * @param categoriaId ID de uma categoria
	 * @return Lista de paginas relacionadas a uma categoria
	 */
	public List<Pagina> getByCategoria(EntityManager entityManager, Integer categoriaId) 
	{		
		Categoria categoria = new CategoriaDAO(entityManager).recuperar(categoriaId);
		
		return getByCategoria(categoria);
	}
	
}
