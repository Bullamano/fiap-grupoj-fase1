package br.com.fiap.needhelpapp.tests;

import java.util.List;
import jakarta.persistence.Persistence;
import jakarta.persistence.EntityManager;
import br.com.fiap.utils.Enums;
import br.com.fiap.dao.PaginaDAO;
import br.com.fiap.dao.ProcedimentoDAO;
import br.com.fiap.needhelpapp.model.Pagina;
import br.com.fiap.needhelpapp.model.Procedimento;

/**
 * Testes referentes a Procedimentos
 */
public class NHAFProcedimentoTests {

	/**
	 * Método principal da classe de testes
	 * @param args args
	 */
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
            	System.out.println("\n" + pag);
            	List<Procedimento> listaProc = procedimentoDAO.getByPagina(pag.getId(), Enums.order.asc);
            	for(Procedimento proc : listaProc){
                	System.out.println("\n\t" + proc);
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
            System.out.println(procedimentoDAO.getByTituloUnique("Top 10 lugares"));
            
            /**
             * Pesquisa por uma string parcial do titulo de um procedimento
             */            
            System.out.println("\nPesquisa por nome (parcial):");
            System.out.println(procedimentoDAO.getByTituloPartial("P"));
            
            /**
             * Criação de objeto no banco
             */
            System.out.println("\nCriando um novo procedimento...");
            entityManager.getTransaction().begin();
            
            Procedimento procedimentoNovo = new Procedimento();
            procedimentoNovo.setTitulo("Como voar");          
            procedimentoNovo.setOrdem(1);
            procedimentoNovo.setTarefas("Se jogue no chão e erre.");
            Pagina paginaProcNovo = paginaDAO.recuperar(1);
            procedimentoNovo.setPagina(paginaProcNovo);
            
            procedimentoDAO.salvar(procedimentoNovo);
            
            entityManager.getTransaction().commit();
            
            System.out.println("\nPesquisando o novo procedimento:");
            System.out.println(procedimentoDAO.getByTituloUnique(procedimentoNovo.getTitulo()));
            List<Procedimento> procPostInsert = procedimentoDAO.listar();
            System.out.println("\nListagem dos procedimentos após o insert:");
            for(Procedimento proc : procPostInsert)
            {
            	System.out.println(proc);
            }
            
            /**
             * Modificando um objeto já presente no banco
             */
            entityManager.getTransaction().begin();
            
            Procedimento procedimentoExistente = procedimentoDAO.getByTituloUnique(procedimentoNovo.getTitulo());
            System.out.println("\nO procedimento " + procedimentoExistente + " será modificado!\n");          
            
            procedimentoExistente.setOrdem(2);
            procedimentoExistente.setTitulo("Como voltar no tempo");
            procedimentoExistente.setTarefas("Passo 1: compre um Delorean...");
            
            Pagina pagProcExistente = paginaDAO.recuperar(2);
            procedimentoExistente.setPagina(pagProcExistente);
            
            procedimentoDAO.salvar(procedimentoExistente);
            
            entityManager.getTransaction().commit();
            
            Procedimento procedimentoRecuperado = procedimentoDAO.recuperar(procedimentoExistente.getId());
            
            System.out.println("");
            System.out.println("\nO procedimento foi modificado: " + procedimentoRecuperado);
            
            /**
             * Deletando o objeto criado acima
             */
            System.out.println("\nDeletando o procedimento criado anteriormente...");
            entityManager.getTransaction().begin();
            
            procedimentoDAO.excluir(procedimentoExistente.getId());
            
            entityManager.getTransaction().commit();
            
            List<Procedimento> procPostDelete = procedimentoDAO.listar();
            System.out.println("\nListagem dos procedimentos após o delete:");
            for(Procedimento proc : procPostDelete)
            {
            	System.out.println(proc);
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
