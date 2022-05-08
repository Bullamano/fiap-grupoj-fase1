package br.com.fiap.needhelpapp.tests;

import java.util.List;
import jakarta.persistence.Persistence;
import jakarta.persistence.EntityManager;
import br.com.fiap.utils.Enums;
import br.com.fiap.dao.PaginaDAO;
import br.com.fiap.dao.ProcedimentoDAO;
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
            System.out.println("\nListagem de todos os procedimentos por página:");
            for(Pagina pag : listaPag){
            	System.out.println(pag);
            	List<Procedimento> listaProc = procedimentoDAO.getByPagina(entityManager, pag.getId(), Enums.order.asc);
            	for(Procedimento proc : listaProc){
                	System.out.println("\t" + proc);
                }
            }
            
            /**
             * Listagem de todos os procedimentos de uma página específica
             */
            System.out.println("\nListagem de todos os procedimentos de uma página específica:");
            Pagina paginaPesquisada = paginaDAO.recuperar(2);
            List<Procedimento> procsPag = procedimentoDAO.getByPagina(paginaPesquisada, Enums.order.irrelevant);
        	for(Procedimento proc : procsPag){
            	System.out.println(proc);
            }
            
            /**
             * Pesquisa por uma string titulo de um procedimento (retorno lista)
             */            
            System.out.println("\nPesquisa por nome (lista):");
            System.out.println(procedimentoDAO.getByTitulo("Ingredientes"));
            
            /**
             * Pesquisa por uma string titulo de um procedimento (retorno único)
             */            
            System.out.println("\nPesquisa por nome (único):");
            System.out.println(procedimentoDAO.getByTituloUnique("Ingredientes"));
            
            /**
             * Pesquisa por uma string parcial do titulo de um procedimento
             */            
            System.out.println("\nPesquisa por nome (parcial):");
            System.out.println(procedimentoDAO.getByTituloPartial("P"));
            
          //TODO adicionar INSERT, UPDATE e DELETE de um registro
            
            
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
