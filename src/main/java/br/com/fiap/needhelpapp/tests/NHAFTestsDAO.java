package br.com.fiap.needhelpapp.tests;

import java.util.List;

import br.com.fiap.dao.*;
import br.com.fiap.needhelpapp.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class NHAFTestsDAO {

	public static void main(String[] args) {
		
		EntityManager entityManager = null;
        try 
        {
        	entityManager =  Persistence.
                createEntityManagerFactory("fiap-grupoj-atividade-fase1").
                createEntityManager();
        	
            PaginaDAO paginaDAO = new PaginaDAO(entityManager);

            List<Pagina> teste = paginaDAO.listar();
            for(Pagina pag : teste)
            {
            	System.out.println(pag.getCategoria());
            }
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
