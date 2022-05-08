package br.com.fiap.needhelpapp.tests;

import java.util.List;

import br.com.fiap.dao.CategoriaDAO;
import br.com.fiap.needhelpapp.model.Categoria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

/**
 * Testes referentes a Categorias
 */
public class NHAFCategoriaTests {
	
	public static void main(String[] args) {
		
		EntityManager entityManager = null;
        try 
        {
        	//Entity Manager
        	entityManager =  Persistence.
                createEntityManagerFactory("need-help-app-fiap").
                createEntityManager();
        	
        	//DAO para o objeto Categoria
        	CategoriaDAO categoriaDAO = new CategoriaDAO(entityManager);

        	/**
        	 * Listagem de todas as entradas no banco de dados
        	 */
            List<Categoria> testeCat = categoriaDAO.listar();
            System.out.println("\nListagem de todas as categorias:");
            for(Categoria categ : testeCat)
            {
            	System.out.println(categ);
            }
            
            /**
             * Pesquisa por uma string nome de uma categoria (retorno lista)
             */            
            System.out.println("\nPesquisa por nome (lista):");
            System.out.println(categoriaDAO.getByName("Roupas"));
            
            /**
             * Pesquisa por uma string nome de uma categoria (retorno único)
             */            
            System.out.println("\nPesquisa por nome (único):");
            System.out.println(categoriaDAO.getByNameUnique("Roupas"));
            
            /**
             * Pesquisa por uma string parcial do nome de uma categoria
             */            
            System.out.println("\nPesquisa por nome (parcial):");
            System.out.println(categoriaDAO.getByNamePartial("p"));
            
            /**
             * Pesquisa por um ID de uma categoria
             */            
            System.out.println("\nPesquisa por ID:");
            System.out.println(categoriaDAO.recuperar(1));
            
            /**
             * Criação de objeto no banco
             */
            entityManager.getTransaction().begin();
            
            Categoria categoriaNova = new Categoria();
            categoriaNova.setNome("Dicas ruins demais");
            
            categoriaDAO.salvar(categoriaNova);    
            
            entityManager.getTransaction().commit();
            
            System.out.println("\nPesquisando a nova categoria:");
            System.out.println(categoriaDAO.getByNameUnique(categoriaNova.getNome()));
            
            List<Categoria> catPostInsert = categoriaDAO.listar();
            System.out.println("\nListagem das categorias após o insert:");
            for(Categoria categ : catPostInsert)
            {
            	System.out.println(categ);
            }
            
            /**
             * Deletando o objeto criado acima
             */
            System.out.println("\nDeletando a categoria criada anteriormente...");
            entityManager.getTransaction().begin();
            
            categoriaDAO.excluir(categoriaNova.getId());
            
            entityManager.getTransaction().commit();
            
            List<Categoria> catPostDelete = categoriaDAO.listar();
            System.out.println("\nListagem das categorias após o delete:");
            for(Categoria categ : catPostDelete)
            {
            	System.out.println(categ);
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
