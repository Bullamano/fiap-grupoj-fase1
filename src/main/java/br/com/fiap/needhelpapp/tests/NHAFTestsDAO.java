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
        	
        	CategoriaDAO categoriaDAO = new CategoriaDAO(entityManager);
        	PaginaDAO paginaDAO = new PaginaDAO(entityManager);
        	ProcedimentoDAO procedimentoDAO = new ProcedimentoDAO(entityManager);
            RecursosDAO recursosDAO = new RecursosDAO(entityManager);

            List<Categoria> testeCat = categoriaDAO.listar();
            List<Pagina> testePag = paginaDAO.listar();
            List<Procedimento> testeProc = procedimentoDAO.listar();
            List<Recursos> testeRec = recursosDAO.listar();
            
            System.out.println("");
            System.out.println("--Categorias--");
            for(Categoria cat : testeCat)
            {
            	System.out.println(cat);
            }
            
            System.out.println("");
            System.out.println("--Páginas--");
            for(Pagina pag : testePag)
            {
            	System.out.println(pag);//.getCategoria());
            }
            
            System.out.println("");
            System.out.println("--Procedimentos--");
            for(Procedimento proc : testeProc)
            {
            	System.out.println(proc);
            }
            
            System.out.println("");
            System.out.println("--Recursos--");
            for(Recursos rec : testeRec)
            {
            	System.out.println(rec);
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
