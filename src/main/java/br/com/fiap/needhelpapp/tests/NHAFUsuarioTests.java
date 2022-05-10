package br.com.fiap.needhelpapp.tests;

import java.util.List;
import br.com.fiap.dao.FavoritoDAO;
import br.com.fiap.dao.PaginaDAO;
import br.com.fiap.dao.UsuarioDAO;
import br.com.fiap.needhelpapp.model.Favorito;
import br.com.fiap.needhelpapp.model.Pagina;
import br.com.fiap.needhelpapp.model.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

/**
 * Testes referentes a Usuarios
 */
public class NHAFUsuarioTests {
	public static void main(String[] args) {
		
		EntityManager entityManager = null;
        try 
        {
        	//Entity Manager
        	entityManager =  Persistence.
                createEntityManagerFactory("need-help-app-fiap").
                createEntityManager();
        	
        	//DAO para os objetos Usuario, Favorito e Pagina
        	UsuarioDAO usuarioDAO = new UsuarioDAO(entityManager);
        	FavoritoDAO favoritoDAO = new FavoritoDAO(entityManager);
        	PaginaDAO paginaDAO = new PaginaDAO(entityManager);
        	
        	/**
        	 * Listagem de todas as entradas no banco de dados
        	 */
            List<Usuario> testeUsr = usuarioDAO.listar();
            System.out.println("\nListagem de todos os usu�rios:");
            for(Usuario usr : testeUsr)
            {
            	System.out.println(usr);
            }
            
            /**
             * Pesquisa por uma string login de um usu�rio (retorno lista)
             */            
            System.out.println("\nPesquisa por login (lista):");
            System.out.println(usuarioDAO.getByLogin("Usu�rio 1"));
            
            /**
             * Pesquisa por uma string login de um usu�rio (retorno �nico)
             */            
            System.out.println("\nPesquisa por login (�nico):");
            System.out.println(usuarioDAO.getByLoginUnique("Usu�rio 2"));
            
            /**
             * Pesquisa por uma string parcial do login de um usu�rio
             */            
            System.out.println("\nPesquisa por nome (parcial):");
            System.out.println(usuarioDAO.getByLoginPartial("Usu"));
            
            /**
             * Pesquisa por um ID de um usu�rio
             */            
            System.out.println("\nPesquisa por ID:");
            System.out.println(usuarioDAO.recuperar(1));
        	
            /**
             * Cria��o de objeto no banco
             */
            entityManager.getTransaction().begin();
            
        	Usuario usuarioNovo = new Usuario();
        	usuarioNovo.setLogin("Sans");
        	usuarioNovo.setSenha("bones");

        	usuarioDAO.salvar(usuarioNovo);
        	
        	entityManager.getTransaction().commit();
        	
        	//Usu�rios no banco ap�s insert
	        List<Usuario> listaUsers = usuarioDAO.listar();
	        for(Usuario user : listaUsers)
	        {
	        	System.out.println(user);
	        }
	        
	        /**
	         * Teste de senha
	         */
	        System.out.println(usuarioNovo.getSenha());
	        System.out.println(usuarioNovo.testSenha("bones"));
	        System.out.println(usuarioNovo.testSenha("determination"));
	        
	        /**
             * Modificando um objeto j� presente no banco
             */
	        entityManager.getTransaction().begin();
	        
	        Usuario usuarioExistente = usuarioDAO.getByLoginUnique(usuarioNovo.getLogin());	        
	        System.out.println("\nO Usu�rio " + usuarioExistente + " ser� modificado!\n");
	        
	        Pagina paginaFavoritoUsuario = paginaDAO.recuperar(3);
	        Favorito favoritoUsuarioExistente = new Favorito();
	        favoritoUsuarioExistente.setPagina(paginaFavoritoUsuario);
	        favoritoUsuarioExistente.setUsuario(usuarioExistente);
	        //Collection<Favorito> favoritoCollection = new ArrayList<Favorito>();
	        //favoritoCollection.add(favoritoUsuarioExistente);
	        favoritoDAO.salvar(favoritoUsuarioExistente);	        
	        
	        usuarioExistente.setLogin("Papyrus");
	        usuarioExistente.setSenha("nyehehe");
	        
	        usuarioDAO.salvar(usuarioExistente);
          
	        entityManager.getTransaction().commit();
	        
	        //TODO verificar por qu� os favoritos est�o como null
	        Usuario usuarioRecuperado = usuarioDAO.recuperar(usuarioExistente.getId());
	        System.out.println("");
          	System.out.println("\nO usuario foi modificado: " + usuarioRecuperado);
          	System.out.println("\nE agora possui:");
          	System.out.println("\n- Login: " + usuarioRecuperado.getLogin());
          	System.out.println("\n- Hash de senha: " + usuarioRecuperado.getSenha());
          	System.out.println("\n- Favoritos: " + usuarioRecuperado.getFavoritos());
          	
          	//TODO exclus�o de registros
          	//usuarioDAO.excluir(usuarioExistente);
          
        }
        catch (Exception e)
        {
			if (entityManager != null && entityManager.getTransaction().isActive())
			{
				entityManager.getTransaction().rollback();
		    }
			e.printStackTrace();
        }
        finally
        {
        	if (entityManager != null)
        	{
     		   entityManager.close();
     	    }
        }   
        
	   System.exit(0);
	}

}
