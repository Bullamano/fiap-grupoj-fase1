package br.com.fiap.needhelpapp.tests;

import br.com.fiap.dao.FavoritoDAO;
import br.com.fiap.dao.PaginaDAO;
import br.com.fiap.dao.UsuarioDAO;
import br.com.fiap.needhelpapp.model.Favorito;
import br.com.fiap.needhelpapp.model.Pagina;
import br.com.fiap.needhelpapp.model.Usuario;

import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

/**
 * Testes unitários para Favorito.
 * Os testes seguem a ordem numérica testXN,
 * na qual X é uma letra (começando sem letra, seguido por A, B e assim por diante)
 * e N é o número ascendente do teste (1 a 9).
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NHAFFavoritoUnitTests {
	
	//Variáveis a serem usadas nos testes
	static List<Favorito> listagemFavoritos, favoritosPorUsuario, favoritosPorPagina, favoritosPostInsert, favoritosPostDelete;
	static Favorito favoritoById, favoritoCreate, favoritoExistente, favoritoRecuperado;
	static Pagina paginaPesquisaFav, paginaFavoritoCreate, paginaFavoritoUpdate;
	static Usuario userPesquisaFav, usuarioFavoritoCreate, usuarioFavoritoUpdate;
	
	EntityManager entityManager = Persistence.createEntityManagerFactory("need-help-app-fiap").createEntityManager();
	
	UsuarioDAO usuarioDAO = new UsuarioDAO(entityManager);
	PaginaDAO paginaDAO = new PaginaDAO(entityManager);
	FavoritoDAO favoritoDAO = new FavoritoDAO(entityManager);
	
	/**
	 * Teste de listagem de favoritos
	 */
	@Test
	public void test1ListagemFavoritos(){
		/**
		* Listagem de todas as entradas no banco de dados
		*/
		listagemFavoritos = favoritoDAO.listar();
	    
	    /**
         * Testes
         */
		Assert.assertEquals(3, listagemFavoritos.size());
	}
	
	/**
	 * Teste de pesquisa de favoritos por ID
	 */
	@Test
	public void test2FavoritoGetById(){
		/**
	     * Pesquisa por um ID de um favorito
	     */
		favoritoById = favoritoDAO.recuperar(1);
	    
	    /**
         * Testes
         */
		Assert.assertEquals("1", favoritoById.getId().toString());
		Assert.assertEquals("1 - Favorito do usuário: 1 - Usuário 1 - Página: 1 - Arroz com feijão", favoritoById.toString());
	}
	
	/**
	 * Teste de pesquisa de favoritos por usuario
	 */
	@Test
	public void test3FavoritoGetByUsuario(){
		/**
         * Pesquisa de favorito por usuario
         */
        userPesquisaFav = usuarioDAO.recuperar(1);
        favoritosPorUsuario = favoritoDAO.getByUsuario(userPesquisaFav);
	    
	    /**
         * Testes
         */
		Assert.assertEquals(2, favoritosPorUsuario.size());
		Assert.assertEquals("1 - Favorito do usuário: 1 - Usuário 1 - Página: 1 - Arroz com feijão", favoritosPorUsuario.get(0).toString());
	}
	
	/**
	 * Teste de pesquisa de favoritos por pagina
	 */
	@Test
	public void test4FavoritoGetByPagina(){
		/**
         * Pesquisa de favorito por pagina
         */
        Pagina paginaPesquisaFav = paginaDAO.recuperar(1);
        favoritosPorPagina = favoritoDAO.getByPagina(paginaPesquisaFav);
	    
	    /**
         * Testes
         */
		Assert.assertEquals(1, favoritosPorPagina.size());
		Assert.assertEquals("1 - Favorito do usuário: 1 - Usuário 1 - Página: 1 - Arroz com feijão", favoritosPorPagina.get(0).toString());
	}
	
	/**
	 * Teste de inserção de objeto
	 */
	@Test
	public void test5FavoritoCreate() {
		/**
         * Criação de objeto no banco
         */
        entityManager.getTransaction().begin();
        
        favoritoCreate = new Favorito();
        
        paginaFavoritoCreate = paginaDAO.recuperar(1);
        favoritoCreate.setPagina(paginaFavoritoCreate);
        
        usuarioFavoritoCreate = usuarioDAO.recuperar(1);
        favoritoCreate.setUsuario(usuarioFavoritoCreate);
        
        favoritoDAO.salvar(favoritoCreate);
    	
    	entityManager.getTransaction().commit();
    	
    	List<Favorito> favoritosPostInsert = favoritoDAO.listar();
		
        /**
         * Testes
         */
		Assert.assertNotNull(favoritoCreate);
		Assert.assertNotNull(favoritoCreate.getId());
		Assert.assertEquals(usuarioFavoritoCreate, favoritoCreate.getUsuario());
		Assert.assertEquals(paginaFavoritoCreate, favoritoCreate.getPagina());
		Assert.assertEquals(4, favoritosPostInsert.size());
	}
	
	/**
	 * Teste de modificação de objeto
	 */
	@Test
	public void test6FavoritoUpdate() {		
		entityManager.getTransaction().begin();
        
        favoritoExistente = favoritoDAO.recuperar(favoritoCreate.getId());
        
        paginaFavoritoUpdate = paginaDAO.recuperar(3);
        favoritoExistente.setPagina(paginaFavoritoUpdate);
        
        usuarioFavoritoUpdate = usuarioDAO.recuperar(2);
        favoritoExistente.setUsuario(usuarioFavoritoUpdate);
        
        favoritoDAO.salvar(favoritoExistente);
        
        entityManager.getTransaction().commit();
        
        favoritoRecuperado = favoritoDAO.recuperar(favoritoExistente.getId());
        
        /**
         * Testes
         */
		Assert.assertNotNull(favoritoExistente);
		Assert.assertNotNull(favoritoRecuperado);
		Assert.assertSame(favoritoExistente, favoritoRecuperado);
		Assert.assertEquals(paginaFavoritoUpdate, favoritoRecuperado.getPagina());
		Assert.assertEquals(usuarioFavoritoUpdate, favoritoRecuperado.getUsuario());
	}
	
	/**
	 * Teste deletando objeto
	 */
	@Test
	public void test7FavoritoDelete() {		
		/**
         * Deletando o objeto criado acima
         */
        entityManager.getTransaction().begin();
        
        favoritoDAO.excluir(favoritoRecuperado.getId());
        
        entityManager.getTransaction().commit();
        
        favoritosPostDelete = favoritoDAO.listar();
        
        /**
         * Testes
         */
        Assert.assertEquals(3, favoritosPostDelete.size());
	}
}
