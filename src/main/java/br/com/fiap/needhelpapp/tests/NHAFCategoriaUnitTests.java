package br.com.fiap.needhelpapp.tests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.com.fiap.dao.CategoriaDAO;
import br.com.fiap.dao.PaginaDAO;
import br.com.fiap.needhelpapp.model.Categoria;
import br.com.fiap.needhelpapp.model.Pagina;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NHAFCategoriaUnitTests {
	
	//Variáveis a serem usadas nos testes
	static List<Categoria> listagemCategoria, categoriaByName, categoriaByNamePartial, categoriasPostInsert, categoriaPostDelete;
	static Collection<Pagina> paginasCollection;	
	static Categoria categoriaByNameUnique, categoriaById, categoriaCreate, categoriaExistente, categoriaRecuperada, categoriaParaDeletar;
	static Pagina paginaExistente;
	
	EntityManager entityManager = Persistence.createEntityManagerFactory("need-help-app-fiap").createEntityManager();
	
	CategoriaDAO categoriaDAO = new CategoriaDAO(entityManager);
	PaginaDAO paginaDAO = new PaginaDAO(entityManager);
	
	/**
	 * Teste de listagem de categorias
	 */
	@Test
	public void test1ListagemCategorias(){
		/**
		* Listagem de todas as entradas no banco de dados
		*/
	    listagemCategoria = categoriaDAO.listar();
	    
	    /**
         * Testes
         */
		Assert.assertEquals(4, listagemCategoria.size());
	}
	
	/**
	 * Teste de pesquisa de categoria por nome
	 */
	@Test
	public void test2CategoriaGetByName(){
		/**
         * Pesquisa por uma string nome de uma categoria (retorno lista)
         */
        categoriaByName = categoriaDAO.getByName("Roupas");
        
        /**
         * Testes
         */
		Assert.assertEquals("3 - Roupas", categoriaByName.get(0).toString());
	}
	
	/**
	 * Teste de pesquisa de categoria por nome único
	 */
	@Test
	public void test3CategoriaGetByNameUnique(){
		/**
         * Pesquisa por uma string nome de uma categoria (retorno único)
         */
        categoriaByNameUnique = categoriaDAO.getByNameUnique("Roupas");
        
        /**
         * Testes
         */
		Assert.assertEquals("Roupas", categoriaByNameUnique.getNome());
	}
	
	/**
	 * Teste de pesquisa de categoria por nome parcial
	 */
	@Test
	public void test4CategoriaGetByNamePartial(){
		/**
         * Pesquisa por uma string parcial do nome de uma categoria
         */
        categoriaByNamePartial = categoriaDAO.getByNamePartial("p");
        
        /**
         * Testes
         */
		Assert.assertEquals(3, categoriaByNamePartial.size());
		Assert.assertEquals("1 - Receitas rápidas", categoriaByNamePartial.get(0).toString());
	}
	
	/**
	 * Teste de pesquisa de categoria por Id
	 */
	@Test
	public void test5CategoriaGetById(){
		/**
         * Pesquisa por um ID de uma categoria
         */
        categoriaById = categoriaDAO.recuperar(1);
        
        /**
         * Testes
         */
		Assert.assertEquals("Receitas rápidas", categoriaById.getNome());
	}
	
	/**
	 * Teste de inserção de objeto
	 */
	@Test
	public void test6CategoriaCreate() {
		/**
         * Criação de objeto no banco
         */
        entityManager.getTransaction().begin();
        categoriaCreate = new Categoria();
        categoriaCreate.setNome("Dicas ruins demais");
        categoriaDAO.salvar(categoriaCreate);
        entityManager.getTransaction().commit();
        System.out.println(categoriaDAO.getByNameUnique(categoriaCreate.getNome()));
        categoriasPostInsert = categoriaDAO.listar();
		
        /**
         * Testes
         */
		Assert.assertNotNull(categoriaCreate);
		Assert.assertTrue(categoriaCreate.getId() != null);
		Assert.assertEquals("Dicas ruins demais", categoriaCreate.getNome());
		Assert.assertEquals(5, categoriasPostInsert.size());
	}
	
	/**
	 * Teste de modificação de objeto
	 */
	@Test
	public void test7CategoriaUpdate() {		
		/**
         * Modificando um objeto já presente no banco
         */
        entityManager.getTransaction().begin();
        categoriaExistente = new Categoria();
        categoriaExistente = categoriaDAO.getByNameUnique("Dicas ruins demais");
        paginaExistente = paginaDAO.recuperar(1);
        paginasCollection = new ArrayList<Pagina>();
        paginasCollection.add(paginaExistente);
        categoriaExistente.setNome("Dicas espetaculares");
        categoriaExistente.setPaginas(paginasCollection);
        categoriaDAO.salvar(categoriaExistente);
        entityManager.getTransaction().commit();
        categoriaRecuperada = categoriaDAO.recuperar(categoriaExistente.getId());
        
        /**
         * Testes
         */
		Assert.assertNotNull(categoriaExistente);
		Assert.assertNotNull(categoriaRecuperada);
		Assert.assertSame(categoriaExistente, categoriaRecuperada);
		Assert.assertEquals("Dicas espetaculares", categoriaRecuperada.getNome());
		Assert.assertNotNull(categoriaRecuperada.getPaginas());
		Assert.assertEquals(1, categoriaRecuperada.getPaginas().size());
	}
	
	/**
	 * Teste deletando objeto
	 */
	@Test
	public void test8CategoriaDelete() {		
		/**
         * Deletando o objeto criado acima
         */
        entityManager.getTransaction().begin();
        categoriaParaDeletar = categoriaDAO.recuperar(categoriaRecuperada.getId());
        categoriaDAO.excluir(categoriaParaDeletar);
        entityManager.getTransaction().commit();
        categoriaPostDelete = categoriaDAO.listar();
        
        /**
         * Testes
         */
        Assert.assertEquals(4, categoriaPostDelete.size());
	}
}
