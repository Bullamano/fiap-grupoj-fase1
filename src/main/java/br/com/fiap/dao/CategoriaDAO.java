package br.com.fiap.dao;

import java.util.Collection;
import java.util.List;
import br.com.fiap.needhelpapp.model.Categoria;
import br.com.fiap.needhelpapp.model.Pagina;
import jakarta.persistence.*;

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
		try {
			return (Categoria)this.em.createQuery(
					"select e from Categoria e where e.nome = :nome"
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
	
	/**
	 * Exclui uma Categoria garantindo a exclusão também de seus Favoritos, Paginas, Procedimentos e Recursos.
	 * @param chave Chave (ID) do banco de um registro.
	 */
	public void excluir(Categoria categoria) {
		PaginaDAO paginaDAO = new PaginaDAO(this.em);
		Collection<Pagina> collectionPaginas = paginaDAO.getByCategoria(categoria);
		for(Pagina pag : collectionPaginas) {
			//Favoritos, Procedimentos e Recursos são excluídos na PaginaDAO
			paginaDAO.excluir(pag);
		}
				
		Integer chave = categoria.getId();
		super.excluir(chave);
	}
}
