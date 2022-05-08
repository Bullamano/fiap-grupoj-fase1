package br.com.fiap.needhelpapp.tests;

import java.util.List;
import br.com.fiap.dao.CategoriaDAO;
import br.com.fiap.dao.PaginaDAO;
import br.com.fiap.needhelpapp.model.Categoria;
import br.com.fiap.needhelpapp.model.Pagina;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class NHAFPaginaTests {
	
public static void main(String[] args) {
		
		EntityManager entityManager = null;
        try 
        {
        	//Entity Manager
        	entityManager =  Persistence.
                createEntityManagerFactory("fiap-grupoj-atividade-fase1").
                createEntityManager();
        	
        	//DAO para os objetos Categoria e Pagina
        	CategoriaDAO categoriaDAO = new CategoriaDAO(entityManager);
        	PaginaDAO paginaDAO = new PaginaDAO(entityManager);

        	/**
        	 * Listagem de todas as entradas no banco de dados
        	 */
            List<Pagina> testePag = paginaDAO.listar();
            System.out.println("\nListagem de todas as p�ginas:");
            for(Pagina pag : testePag)
            {
            	System.out.println(pag);
            }
            
            /**
             * Pesquisa por uma string nome de uma pagina (retorno lista)
             */            
            System.out.println("\nPesquisa por nome (lista):");
            System.out.println(paginaDAO.getByName("Lasagna"));
            
            /**
             * Pesquisa por uma string nome de uma pagina (retorno �nico)
             */            
            System.out.println("\nPesquisa por nome (�nico):");
            System.out.println(paginaDAO.getByNameUnique("N�s de gravata"));
            
            /**
             * Pesquisa por uma string parcial do nome de uma p�gina
             */            
            System.out.println("\nPesquisa por nome (parcial):");
            System.out.println(paginaDAO.getByNamePartial("Como"));
            
            /**
             * Pesquisa por um ID de uma p�gina
             */            
            System.out.println("\nPesquisa por ID:");
            System.out.println(paginaDAO.recuperar(1));
            
            /**
             * Pesquisa de p�gina por um ID de uma categoria
             */            
            System.out.println("\nPesquisa por categoria (ID):");
            List<Pagina> pesquisaCategId = paginaDAO.getByCategoria(1);
            for(Pagina pag : pesquisaCategId)
            {
            	System.out.println(pag);
            }
            
            /**
             * Pesquisa de p�gina por um objeto categoria
             */            
            System.out.println("\nPesquisa por categoria (objeto):");
            Categoria categObj = categoriaDAO.recuperar(1);
            List<Pagina> pesquisaCategObj = paginaDAO.getByCategoria(categObj);
            for(Pagina pag : pesquisaCategObj)
            {
            	System.out.println(pag);
            }
            
            /**
             * Cria��o de objeto no banco
             */
            entityManager.getTransaction().begin();
            
            Pagina paginaNova = new Pagina();
            paginaNova.setNome("P�gina in�til");
            Categoria categPagNova = categoriaDAO.recuperar(1);
            paginaNova.setCategoria(categPagNova);
            
            paginaDAO.salvar(paginaNova);
            
            entityManager.getTransaction().commit();
            
            System.out.println("\nPesquisando a nova p�gina:");
            System.out.println(paginaDAO.getByNameUnique(paginaNova.getNome()));
            List<Pagina> pagPostInsert = paginaDAO.listar();
            System.out.println("\nListagem das p�ginas ap�s o insert:");
            for(Pagina pag : pagPostInsert)
            {
            	System.out.println(pag);
            }
            
            /**
             * Deletando o objeto criado acima
             */
            System.out.println("\nDeletando a p�gina criada anteriormente...");
            entityManager.getTransaction().begin();
            
            paginaDAO.excluir(paginaNova.getId());
            
            entityManager.getTransaction().commit();
            
            List<Pagina> pagPostDelete = paginaDAO.listar();
            System.out.println("\nListagem das p�ginas ap�s o delete:");
            for(Pagina pag : pagPostDelete)
            {
            	System.out.println(pag);
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
