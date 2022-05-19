package br.com.fiap.needhelpapp.tests;

import br.com.fiap.dao.FavoritoDAO;
import br.com.fiap.dao.PaginaDAO;
import br.com.fiap.dao.UsuarioDAO;
import br.com.fiap.needhelpapp.model.Favorito;
import br.com.fiap.needhelpapp.model.Pagina;
import br.com.fiap.needhelpapp.model.Usuario;

import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

/**
 * Testes unit�rios para Usuario.
 * Os testes seguem a ordem num�rica testXN,
 * na qual X � uma letra (come�ando sem letra, seguido por A, B e assim por diante)
 * e N � o n�mero ascendente do teste (1 a 9).
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NHAFUsuarioUnitTests {
	
	//Vari�veis a serem usadas nos testes
	static List<Usuario> listagemUsuario, usuarioByLogin, usuarioByLoginPartial, usuariosPostInsert, usuariosPostDelete;
	static Collection<Favorito> favoritoCollection;
	static Usuario usuarioByLoginUnique, usuarioById, usuarioCreate, usuarioExistente, usuarioRecuperado;
	static Favorito favoritoUsuarioExistente;
	static Pagina paginaFavoritoUsuario;
	
	EntityManager entityManager = Persistence.createEntityManagerFactory("need-help-app-fiap").createEntityManager();
	
	UsuarioDAO usuarioDAO = new UsuarioDAO(entityManager);
	FavoritoDAO favoritoDAO = new FavoritoDAO(entityManager);
	PaginaDAO paginaDAO = new PaginaDAO(entityManager);
	
	/**
	 * Teste de listagem de usuarios
	 */
	@Test
	public void test1ListagemUsuario(){
		/**
		* Listagem de todas as entradas no banco de dados
		*/
		listagemUsuario = usuarioDAO.listar();
	    
	    /**
         * Testes
         */
		Assert.assertEquals(2, listagemUsuario.size());
	}
	
	/**
	 * Teste de pesquisa de usuario por login
	 */
	@Test
	public void test2UsuarioGetByLogin(){
		/**
         * Pesquisa por uma string login de um usu�rio (retorno lista)
         */
        usuarioByLogin = usuarioDAO.getByLogin("Usu�rio 1");
        
        /**
         * Testes
         */
		Assert.assertEquals("1 - Usu�rio 1", usuarioByLogin.get(0).toString());
	}
	
	/**
	 * Teste de pesquisa de usuario por login �nico
	 */
	@Test
	public void test3UsuarioGetByLoginUnique(){
		/**
         * Pesquisa por uma string login de um usu�rio (retorno �nico)
         */
        usuarioByLoginUnique = usuarioDAO.getByLoginUnique("Usu�rio 2");
        
        /**
         * Testes
         */
		Assert.assertEquals("Usu�rio 2", usuarioByLoginUnique.getLogin());
	}
	
	/**
	 * Teste de pesquisa de usuario por login parcial
	 */
	@Test
	public void test4UsuarioGetByLoginPartial(){
		/**
         * Pesquisa por uma string parcial do login de um usu�rio
         */
        usuarioByLoginPartial = usuarioDAO.getByLoginPartial("Usu");
        
        /**
         * Testes
         */
		Assert.assertEquals(2, usuarioByLoginPartial.size());
		Assert.assertEquals("1 - Usu�rio 1", usuarioByLoginPartial.get(0).toString());
	}
	
	/**
	 * Teste de pesquisa de usuario por Id
	 */
	@Test
	public void test5UsuarioGetById(){
		/**
         * Pesquisa por um ID de um usu�rio
         */
        usuarioById = usuarioDAO.recuperar(1);
        
        /**
         * Testes
         */
		Assert.assertEquals("Usu�rio 1", usuarioById.getLogin());
	}
	
	/**
	 * Teste de inser��o de objeto
	 */
	@Test
	public void test6UsuarioCreate() {
		/**
         * Cria��o de objeto no banco
         */
        entityManager.getTransaction().begin();
        
    	usuarioCreate = new Usuario();
    	usuarioCreate.setLogin("Sans");
    	usuarioCreate.setEmail("skeleton@underground.com");
    	usuarioCreate.setSenha("bones");

    	usuarioDAO.salvar(usuarioCreate);
    	
    	entityManager.getTransaction().commit();
    	
    	//Usu�rios no banco ap�s insert
        usuariosPostInsert = usuarioDAO.listar();
		
        /**
         * Testes
         */
		Assert.assertNotNull(usuarioCreate);
		Assert.assertNotNull(usuarioCreate.getId());
		Assert.assertEquals("Sans", usuarioCreate.getLogin());
		Assert.assertEquals("skeleton@underground.com", usuarioCreate.getEmail());
		Assert.assertEquals(3, usuariosPostInsert.size());
	}
	
	/**
	 * Testes de senha
	 */
	@Test
	public void test7SenhaUsuario() {
		/**
         * Testes de senha
         */
        String testeSenha = usuarioCreate.getSenha();
        Boolean senhaIsCorrect = usuarioCreate.testSenha("bones");
        Boolean senhaIsWrong = usuarioCreate.testSenha("determination");
		
        /**
         * Testes
         */
		Assert.assertNotNull(testeSenha);
		Assert.assertTrue(senhaIsCorrect);
		Assert.assertFalse(senhaIsWrong);
	}
	
	/**
	 * Teste de modifica��o de objeto
	 */
	@Test
	public void test8UsuarioUpdate() {		
		entityManager.getTransaction().begin();
        
        usuarioExistente = usuarioDAO.getByLoginUnique(usuarioCreate.getLogin());
        
        paginaFavoritoUsuario = paginaDAO.recuperar(3);
        favoritoUsuarioExistente = new Favorito();
        favoritoUsuarioExistente.setPagina(paginaFavoritoUsuario);
        favoritoUsuarioExistente.setUsuario(usuarioExistente);
        
        favoritoCollection = usuarioExistente.getFavoritos();
        favoritoCollection.add(favoritoUsuarioExistente);
        
        usuarioExistente.setFavoritos(favoritoCollection);      
        
        usuarioExistente.setLogin("Papyrus");
        usuarioExistente.setEmail("guard@underground.com");
        usuarioExistente.setSenha("nyehehe");
        
        usuarioDAO.salvar(usuarioExistente);
      
        entityManager.getTransaction().commit();
        
        //Salvamento do Favorito separadamente
        //(usar mais de um salvar por Transaction pode causar erros)
        entityManager.getTransaction().begin();
        
        favoritoDAO.salvar(favoritoUsuarioExistente);
        
        entityManager.getTransaction().commit();
        
        usuarioRecuperado = usuarioDAO.recuperar(usuarioExistente.getId());
        
        /**
         * Testes
         */
		Assert.assertNotNull(usuarioExistente);
		Assert.assertNotNull(usuarioRecuperado);
		Assert.assertSame(usuarioExistente, usuarioRecuperado);
		Assert.assertEquals("Papyrus", usuarioRecuperado.getLogin());
		Assert.assertEquals("guard@underground.com", usuarioRecuperado.getEmail());
		Assert.assertTrue(usuarioRecuperado.testSenha("nyehehe"));
		Assert.assertEquals(1, usuarioRecuperado.getFavoritos().size());
	}
	
	/**
	 * Teste deletando objeto
	 */
	@Test
	public void test9UsuarioDelete() {		
		/**
         * Deletando o objeto criado acima
         */
        entityManager.getTransaction().begin();
        
        //M�todo diferente do presente na GenericDAO pois este exclui primeiramente
        //os favoritos do usu�rio e s� depois o exclui, prevenindo erros
        usuarioDAO.excluir(usuarioRecuperado);
        
        entityManager.getTransaction().commit();
        
        usuariosPostDelete = usuarioDAO.listar();
        
        /**
         * Testes
         */
        Assert.assertEquals(2, usuariosPostDelete.size());
	}
}
