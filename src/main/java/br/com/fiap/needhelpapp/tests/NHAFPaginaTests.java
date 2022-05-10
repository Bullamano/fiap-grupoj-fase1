package br.com.fiap.needhelpapp.tests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import br.com.fiap.dao.*;
import br.com.fiap.needhelpapp.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

/**
 * Testes referentes a Paginas
 */
public class NHAFPaginaTests {
	
	public static void main(String[] args) {
		
		EntityManager entityManager = null;
        try 
        {
        	//Entity Manager
        	entityManager =  Persistence.
                createEntityManagerFactory("need-help-app-fiap").
                createEntityManager();
        	
        	//DAO para os objetos Categoria e Pagina
        	CategoriaDAO categoriaDAO = new CategoriaDAO(entityManager);
        	PaginaDAO paginaDAO = new PaginaDAO(entityManager);
        	ProcedimentoDAO procedimentoDAO = new ProcedimentoDAO(entityManager);
        	RecursosDAO recursosDAO = new RecursosDAO(entityManager);
        	FavoritoDAO favoritoDAO = new FavoritoDAO(entityManager);

        	/**
        	 * Listagem de todas as entradas no banco de dados
        	 */
            List<Pagina> testePag = paginaDAO.listar();
            System.out.println("\nListagem de todas as páginas:");
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
             * Pesquisa por uma string nome de uma pagina (retorno único)
             */            
            System.out.println("\nPesquisa por nome (único):");
            System.out.println(paginaDAO.getByNameUnique("Nós de gravata"));
            
            /**
             * Pesquisa por uma string parcial do nome de uma página
             */            
            System.out.println("\nPesquisa por nome (parcial):");
            System.out.println(paginaDAO.getByNamePartial("Como"));
            
            /**
             * Pesquisa por um ID de uma página
             */            
            System.out.println("\nPesquisa por ID:");
            System.out.println(paginaDAO.recuperar(1));
            
            /**
             * Pesquisa de página por um ID de uma categoria
             */            
            System.out.println("\nPesquisa por categoria (ID):");
            List<Pagina> pesquisaCategId = paginaDAO.getByCategoria(1);
            for(Pagina pag : pesquisaCategId)
            {
            	System.out.println(pag);
            }
            
            /**
             * Pesquisa de página por um objeto categoria
             */            
            System.out.println("\nPesquisa por categoria (objeto):");
            Categoria categObj = categoriaDAO.recuperar(1);
            List<Pagina> pesquisaCategObj = paginaDAO.getByCategoria(categObj);
            for(Pagina pag : pesquisaCategObj)
            {
            	System.out.println(pag);
            }
            
            /**
             * Criação de objeto no banco
             */
            entityManager.getTransaction().begin();
            
            Pagina paginaNova = new Pagina();
            paginaNova.setNome("Página inútil");
            Categoria categPagNova = categoriaDAO.recuperar(1);
            paginaNova.setCategoria(categPagNova);
            
            paginaDAO.salvar(paginaNova);
            
            entityManager.getTransaction().commit();
            
            System.out.println("\nPesquisando a nova página:");
            System.out.println(paginaDAO.getByNameUnique(paginaNova.getNome()));
            List<Pagina> pagPostInsert = paginaDAO.listar();
            System.out.println("\nListagem das páginas após o insert:");
            for(Pagina pag : pagPostInsert)
            {
            	System.out.println(pag);
            }
            
            /**
             * Modificando um objeto já presente no banco
             */
            entityManager.getTransaction().begin();
            
            Pagina paginaExistente = paginaDAO.getByNameUnique(paginaNova.getNome());
            System.out.println("\nA página " + paginaExistente + " será modificada!\n");
            
            Procedimento procedimentoExistente = procedimentoDAO.recuperar(1);
            Collection<Procedimento> procedimentosCollection = new ArrayList<Procedimento>();
            procedimentosCollection.add(procedimentoExistente);
            
            Recursos recursosExistente = recursosDAO.recuperar(1);
            Collection<Recursos> recursosCollection = new ArrayList<Recursos>();
            recursosCollection.add(recursosExistente);
            
            Favorito favoritosExistente = favoritoDAO.recuperar(1);
            Collection<Favorito> favoritoCollection = new ArrayList<Favorito>();
            favoritoCollection.add(favoritosExistente);
            
            paginaExistente.setNome("Página maravilhosa");
            paginaExistente.setProcedimentos(procedimentosCollection);
            paginaExistente.setRecursos(recursosCollection);
            paginaExistente.setFavoritos(favoritoCollection);
            
            paginaDAO.salvar(paginaExistente);
            
            entityManager.getTransaction().commit();
            
            Pagina paginaRecuperada = paginaDAO.recuperar(paginaExistente.getId());
            
            System.out.println("");
            System.out.println("\nA página foi modificada: " + paginaRecuperada);
            System.out.println("\nE agora possui:");
            System.out.println("\n\tProcedimentos: " + paginaRecuperada.getProcedimentos());
            System.out.println("\n\tRecursos: " + paginaRecuperada.getRecursos());
            System.out.println("\n\tFavoritos: " + paginaRecuperada.getFavoritos());
            
            /**
             * Deletando o objeto criado acima
             */
            System.out.println("\nDeletando a página criada anteriormente...");
            entityManager.getTransaction().begin();
            
            paginaDAO.excluir(paginaRecuperada.getId());
            
            entityManager.getTransaction().commit();
            
            List<Pagina> pagPostDelete = paginaDAO.listar();
            System.out.println("\nListagem das páginas após o delete:");
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
