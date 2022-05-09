package br.com.fiap.needhelpapp.tests;

import java.util.List;

import br.com.fiap.dao.UsuarioDAO;
import br.com.fiap.needhelpapp.model.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class NHAFUsuarioTests {
	public static void main(String[] args) {
		
		EntityManager entityManager = null;
        try 
        {
        	//Entity Manager
        	entityManager =  Persistence.
                createEntityManagerFactory("need-help-app-fiap").
                createEntityManager();
        	
        	UsuarioDAO usuarioDAO = new UsuarioDAO(entityManager);

            entityManager.getTransaction().begin();
            
        	Usuario userTeste = new Usuario();
        	userTeste.setLogin("Nome Teste");
        	userTeste.setSenha("senha fraca");

        	usuarioDAO.salvar(userTeste);
        	
        	entityManager.getTransaction().commit();
          
	        List<Usuario> listaUsers = usuarioDAO.listar();
	        for(Usuario user : listaUsers)
	        {
	        	System.out.println(user);
	        }
	        
	        System.out.println(userTeste.getSenha());
	        System.out.println(userTeste.testSenha("senha"));
	        System.out.println(userTeste.testSenha("senha fraca"));
          
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
