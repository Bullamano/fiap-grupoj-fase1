package br.com.fiap.needhelpapp.tests;

import java.util.List;
import br.com.fiap.utils.Enums;
import br.com.fiap.dao.PaginaDAO;
import br.com.fiap.dao.ProcedimentoDAO;
import jakarta.persistence.Persistence;
import jakarta.persistence.EntityManager;
import br.com.fiap.needhelpapp.model.Pagina;
import br.com.fiap.needhelpapp.model.Procedimento;

public class NHAFProcedimentoTests {

	public static void main(String[] args) {
		
		EntityManager entityManager = null;
        try 
        {
        	//Entity Manager
        	entityManager =  Persistence.
                createEntityManagerFactory("need-help-app-fiap").
                createEntityManager();
        	
        	//DAO para os objetos Pagina e Procedimento
        	PaginaDAO paginaDAO = new PaginaDAO(entityManager);
        	ProcedimentoDAO procedimentoDAO = new ProcedimentoDAO(entityManager);

        	/**
        	 * Listagem de todas as entradas no banco de dados
        	 */
            List<Procedimento> testeProc = procedimentoDAO.listar();
            System.out.println("\nListagem de todos os procedimentos:");
            for(Procedimento proc : testeProc)
            {
            	System.out.println(proc);
            }
            
            /**
             * Listagem de todos os procedimentos por página
             */
            List<Pagina> listaPag = paginaDAO.listar();
            System.out.println("\nListagem de todos os procedimentos de uma página:");
            for(Pagina pag : listaPag){
            	System.out.println(pag);
            	List<Procedimento> listaProc = procedimentoDAO.getByPagina(entityManager, pag.getId(), Enums.order.asc);
            	for(Procedimento proc : listaProc){
                	System.out.println("\t" + proc);
                }
            }
            
            //TODO completar os testes de procedimentos (e de outras classes)
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
