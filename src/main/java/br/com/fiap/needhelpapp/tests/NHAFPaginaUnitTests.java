package br.com.fiap.needhelpapp.tests;

import br.com.fiap.dao.*;
import br.com.fiap.needhelpapp.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

/**
 * Testes unitários para Categoria.
 * Os testes seguem a ordem numérica testXN,
 * na qual X é uma letra (começando sem letra, seguido por A, B e assim por diante)
 * e N é o número ascendente do teste (1 a 9).
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NHAFPaginaUnitTests {
	
	//Variáveis a serem usadas nos testes
	static List<Pagina> listagemPagina, paginaByName, paginaByNamePartial, pesquisaCategId,
						pesquisaCategObj, paginasByNomeAutor, paginasByIdAutor, paginasPostInsert, paginasPostDelete;
	static Collection<Procedimento> procedimentosCollection;
	static Collection<Recursos> recursosCollection;
	static Collection<Favorito> favoritoCollection;
	static Categoria categObj, categPagCreate, categPagEterna;
	static Pagina paginaByNameUnique, paginaById, paginaCreate, paginaExistente, paginaRecuperada, paginaEterna, paginaSemUser;
	static Usuario usuarioPagCreate, usuarioPagExistente, usuarioPagEterna, usuarioInexistente;
	static Procedimento procedimentoExistente;
	static Recursos recursosExistente;
	static Favorito favoritoExistente;
	
	EntityManager entityManager = Persistence.createEntityManagerFactory("need-help-app-fiap").createEntityManager();
	
	CategoriaDAO categoriaDAO = new CategoriaDAO(entityManager);
	PaginaDAO paginaDAO = new PaginaDAO(entityManager);
	ProcedimentoDAO procedimentoDAO = new ProcedimentoDAO(entityManager);
	RecursosDAO recursosDAO = new RecursosDAO(entityManager);
	FavoritoDAO favoritoDAO = new FavoritoDAO(entityManager);
	UsuarioDAO usuarioDAO = new UsuarioDAO(entityManager);
	
	/**
	 * Teste de listagem de páginas
	 */
	@Test
	public void test1ListagemPaginas(){
		/**
		* Listagem de todas as entradas no banco de dados
		*/
		listagemPagina = paginaDAO.listar();
	    
	    /**
         * Testes
         */
		Assert.assertEquals(6, listagemPagina.size());
	}
	
	/**
	 * Teste de pesquisa de pagina por nome
	 */
	@Test
	public void test2PaginaGetByName(){
		/**
         * Pesquisa por uma string nome de uma pagina (retorno lista)
         */
        paginaByName = paginaDAO.getByName("Lasagna");
        
        /**
         * Testes
         */
		Assert.assertEquals("2 - Lasagna", paginaByName.get(0).toString());
	}
	
	/**
	 * Teste de pesquisa de pagina por nome único
	 */
	@Test
	public void test3PaginaGetByNameUnique(){
		/**
         * Pesquisa por uma string nome de uma pagina (retorno único)
         */  
        paginaByNameUnique = paginaDAO.getByNameUnique("Nós de gravata");
        
        /**
         * Testes
         */
		Assert.assertEquals("Nós de gravata", paginaByNameUnique.getNome());
	}
	
	/**
	 * Teste de pesquisa de pagina por nome parcial
	 */
	@Test
	public void test4PaginaGetByNamePartial(){
		/**
         * Pesquisa por uma string parcial do nome de uma página
         */
        paginaByNamePartial = paginaDAO.getByNamePartial("Como");
        
        /**
         * Testes
         */
		Assert.assertEquals(2, paginaByNamePartial.size());
		Assert.assertEquals("3 - Como desentupir uma privada", paginaByNamePartial.get(0).toString());
	}
	
	/**
	 * Teste de pesquisa de pagina por Id
	 */
	@Test
	public void test5PaginaGetById(){
		/**
         * Pesquisa por um ID de uma pagina
         */
        paginaById = paginaDAO.recuperar(1);
        
        /**
         * Testes
         */
		Assert.assertEquals("Arroz com feijão", paginaById.getNome());
	}
	
	/**
	 * Teste de pesquisa de pagina por Id de uma categoria
	 */
	@Test
	public void test6PaginaGetByCategoriaId(){
		/**
         * Pesquisa de paginas pelo ID de uma categoria
         */
        pesquisaCategId = paginaDAO.getByCategoria(1);
        
        /**
         * Testes
         */
		Assert.assertEquals(2, pesquisaCategId.size());
	}
	
	/**
	 * Teste de pesquisa de pagina por categoria (objeto)
	 */
	@Test
	public void test7PaginaByCategoria(){
		/**
         * Pesquisa de paginas por categoria
         */
		categObj = categoriaDAO.recuperar(1);
        pesquisaCategObj = paginaDAO.getByCategoria(categObj);
        
        /**
         * Testes
         */
        Assert.assertEquals(2, pesquisaCategId.size());
        Assert.assertEquals(pesquisaCategId.toString(), pesquisaCategObj.toString());
	}
	
	/**
	 * Teste de pesquisa de pagina pelo nome (parcial) do autor
	 */
	@Test
	public void test8PaginaGetByNomeAutor(){
		/**
         * Pesquisa por nome de autor de uma pagina
         */
        paginasByNomeAutor = paginaDAO.getByNomeAutor("1");
        
        /**
         * Testes
         */
        Assert.assertEquals(5, paginasByNomeAutor.size());
        Assert.assertTrue(paginaByNamePartial.get(0).getNomeAutor().contains("1"));
	}
	
	/**
	 * Teste de pesquisa de pagina pelo ID do autor
	 */
	@Test
	public void test9PaginaGetByIdAutor(){
		/**
         * Pesquisa por um ID do autor de uma pagina
         */
        paginasByIdAutor = paginaDAO.getByIdAutor(2);
        
        /**
         * Testes
         */
        Assert.assertEquals(1, paginasByIdAutor.size());
        Assert.assertEquals(2, paginasByIdAutor.get(0).getIdAutor());
	}
	
	/**
	 * Teste de inserção de objeto
	 */
	@Test
	public void testA1PaginaCreate() {
		/**
         * Criação de objeto no banco
         */
		entityManager.getTransaction().begin();
		
        paginaCreate = new Pagina();
        paginaCreate.setNome("Página inútil");
        
        categPagCreate = categoriaDAO.recuperar(1);
        paginaCreate.setCategoria(categPagCreate);
        
        usuarioPagCreate = usuarioDAO.recuperar(1);
        paginaCreate.setAutor(usuarioPagCreate);
        
        paginaDAO.salvar(paginaCreate);
        
        entityManager.getTransaction().commit();
        
        paginasPostInsert = paginaDAO.listar();
		
        /**
         * Testes
         */
		Assert.assertNotNull(paginasPostInsert);
		Assert.assertTrue(paginaCreate.getId() != null);
		Assert.assertEquals("Página inútil", paginaCreate.getNome());
		Assert.assertEquals(7, paginasPostInsert.size());
	}
	
	/**
	 * Teste de modificação de objeto
	 */
	@Test
	public void testA2PaginaUpdate() {		
		/**
         * Modificando um objeto já presente no banco
         */
		entityManager.getTransaction().begin();        
        
		paginaExistente = new Pagina();
        paginaExistente = paginaDAO.getByNameUnique("Página inútil");
        
        procedimentoExistente = new Procedimento();
        procedimentoExistente = procedimentoDAO.recuperar(1);
        procedimentosCollection = new ArrayList<Procedimento>();
        procedimentosCollection.add(procedimentoExistente);
        
        recursosExistente = new Recursos();
        recursosExistente = recursosDAO.recuperar(1);
        recursosCollection = new ArrayList<Recursos>();
        recursosCollection.add(recursosExistente);
        
        favoritoExistente = new Favorito();
        favoritoExistente = favoritoDAO.recuperar(1);
        favoritoCollection = new ArrayList<Favorito>();
        favoritoCollection.add(favoritoExistente);
        
        usuarioPagExistente = usuarioDAO.recuperar(2);
        
        paginaExistente.setNome("Página maravilhosa");
        paginaExistente.setProcedimentos(procedimentosCollection);
        paginaExistente.setRecursos(recursosCollection);
        paginaExistente.setFavoritos(favoritoCollection);
        paginaExistente.setAutor(usuarioPagExistente);
        
        paginaDAO.salvar(paginaExistente);
        
        entityManager.getTransaction().commit();
        
        paginaRecuperada = paginaDAO.recuperar(paginaExistente.getId());
        
        /**
         * Testes
         */
		Assert.assertNotNull(paginaExistente);
		Assert.assertNotNull(paginaRecuperada);
		Assert.assertSame(paginaExistente, paginaRecuperada);
		Assert.assertEquals("Página maravilhosa", paginaRecuperada.getNome());
		Assert.assertEquals(1, paginaRecuperada.getProcedimentos().size());
		Assert.assertEquals(1, paginaRecuperada.getRecursos().size());
		Assert.assertEquals(1, paginaRecuperada.getFavoritos().size());
		Assert.assertEquals(usuarioPagExistente, paginaRecuperada.getAutor(entityManager));
		Assert.assertEquals(2, paginaRecuperada.getIdAutor());
		Assert.assertEquals("Usuário 2", paginaRecuperada.getNomeAutor());
	}
	
	/**
	 * Teste deletando objeto
	 */
	@Test
	public void testA3PaginaDelete() {		
		/**
         * Deletando o objeto criado acima
         */
		entityManager.getTransaction().begin();
        
        paginaDAO.excluir(paginaRecuperada);
        
        entityManager.getTransaction().commit();
        
        paginasPostDelete = paginaDAO.listar();
        
        /**
         * Testes
         */
        Assert.assertEquals(6, paginasPostDelete.size());
	}
	
	/**
	 * Teste deletando objeto
	 */
	@Test
	public void testA4PaginaComUsuarioDeletado() {
		/**
         * Criando uma página e deletando seu usuário
         * (páginas devem ser mantidas mesmo após a exclusão de seu autor)
         */
        //Criação
        entityManager.getTransaction().begin();
        
        usuarioPagEterna = new Usuario();
        usuarioPagEterna.setLogin("User volátil");
        usuarioPagEterna.setSenha("senhaSecreta");
        usuarioPagEterna.setEmail("email@email.com");
        
        usuarioDAO.salvar(usuarioPagEterna);
        
        entityManager.getTransaction().commit();
        
        //Salvamento da Pagina separadamente
        //(usar mais de um salvar por Transaction pode causar erros)
        entityManager.getTransaction().begin();
        
        paginaEterna = new Pagina();
        paginaEterna.setNome("Página eterna");
        
        categPagEterna = categoriaDAO.recuperar(1);
        paginaEterna.setCategoria(categPagEterna);
        
        paginaEterna.setAutor(usuarioPagEterna);
        
        paginaDAO.salvar(paginaEterna);
        
        entityManager.getTransaction().commit();
        
        /**
         * Testes após criação
         */
        Assert.assertNotNull(paginaEterna);
        Assert.assertNotNull(paginaEterna.getAutor(entityManager));
        
        //Exclusão do usuário
        entityManager.getTransaction().begin();
        
        usuarioDAO.excluir(usuarioPagEterna);
        
        entityManager.getTransaction().commit();
        
        paginaSemUser = new Pagina();
        paginaSemUser = paginaDAO.getByNameUnique(paginaEterna.getNome());
        
        usuarioInexistente = new Usuario();
        usuarioInexistente = usuarioDAO.recuperar(usuarioPagEterna.getId());
        
        /**
         * Testes após exclusão do usuário
         */
        Assert.assertNotNull(paginaSemUser);
        Assert.assertNotNull(paginaSemUser.getNomeAutor());
        Assert.assertNotNull(paginaSemUser.getIdAutor());
        Assert.assertNull(usuarioInexistente);
        
        
        //Exclusão da página para retornar o banco ao estado original
        entityManager.getTransaction().begin();
        
        paginaDAO.excluir(paginaSemUser);
        
        entityManager.getTransaction().commit();
	}
}
