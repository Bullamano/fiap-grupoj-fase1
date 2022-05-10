package br.com.fiap.needhelpapp.tests;

import java.util.Collection;
import java.util.List;

import br.com.fiap.dao.FavoritoDAO;
import br.com.fiap.dao.PaginaDAO;
import br.com.fiap.dao.UsuarioDAO;
import br.com.fiap.needhelpapp.model.Favorito;
import br.com.fiap.needhelpapp.model.Pagina;
import br.com.fiap.needhelpapp.model.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class NHAFFavoritoTests {

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
        	PaginaDAO paginaDAO = new PaginaDAO(entityManager);
        	FavoritoDAO favoritoDAO = new FavoritoDAO(entityManager);
        	
        	/**
        	 * Listagem de todas as entradas no banco de dados
        	 */
            List<Favorito> testeFav = favoritoDAO.listar();
            System.out.println("\nListagem de todos os favoritos:");
            for(Favorito fav : testeFav)
            {
            	System.out.println(fav);
            }
                        
            /**
             * Pesquisa por um ID de um favorito
             */            
            System.out.println("\nPesquisa por ID:");
            System.out.println(favoritoDAO.recuperar(1));
            
            /**
             * Pesquisa de favorito por usuario
             */
            Usuario userPesquisaFav = usuarioDAO.recuperar(1);
            System.out.println("\nPesquisa por Usuário:");
            Collection<Favorito> favoritosPorUsuario = favoritoDAO.getByUsuario(userPesquisaFav);
            for(Favorito fav : favoritosPorUsuario) {
            	System.out.println(fav);
            }
            
            /**
             * Pesquisa de favorito por pagina
             */
            Pagina pagPesquisaFav = paginaDAO.recuperar(1);
            System.out.println("\nPesquisa por Página:");
            Collection<Favorito> favoritosPorPagina = favoritoDAO.getByPagina(pagPesquisaFav);
            for(Favorito fav : favoritosPorPagina) {
            	System.out.println(fav);
            }
        	
            /**
             * Criação de objeto no banco
             */
            entityManager.getTransaction().begin();
            
            Favorito favoritoNovo = new Favorito();
            
            Pagina paginaDoFavorito = paginaDAO.recuperar(1);
            favoritoNovo.setPagina(paginaDoFavorito);
            
            Usuario usuarioDoFavorito = usuarioDAO.recuperar(1);
            favoritoNovo.setUsuario(usuarioDoFavorito);
            
            favoritoDAO.salvar(favoritoNovo);
        	
        	entityManager.getTransaction().commit();
        	
        	//Favoritos no banco após insert
	        List<Favorito> listaFavs = favoritoDAO.listar();
	        for(Favorito fav : listaFavs)
	        {
	        	System.out.println(fav);
	        }
	        
	        /**
             * Modificando um objeto já presente no banco
             */
	        entityManager.getTransaction().begin();
	        
	        Favorito favoritoExistente = favoritoDAO.recuperar(favoritoNovo.getId());	        
	        System.out.println("\nO Favorito " + favoritoExistente + " será modificado!\n");
	        
	        Pagina paginaFavoritoUpdate = paginaDAO.recuperar(3);
	        favoritoExistente.setPagina(paginaFavoritoUpdate);
	        
	        Usuario usuarioFavoritoUpdate = usuarioDAO.recuperar(2);
	        favoritoExistente.setUsuario(usuarioFavoritoUpdate);
	        
	        favoritoDAO.salvar(favoritoExistente);
	        
	        entityManager.getTransaction().commit();
	        
	        Favorito favoritoRecuperado = favoritoDAO.recuperar(favoritoExistente.getId());

	        System.out.println("");
          	System.out.println("\nO favorito foi modificado: " + favoritoRecuperado);
          	System.out.println("\nE agora possui:");
          	System.out.println("\n- Usuário: " + favoritoRecuperado.getUsuario());
          	System.out.println("\n- Página: " + favoritoRecuperado.getPagina());
          	
          	/**
             * Deletando o objeto criado acima
             */
          	System.out.println("\nDeletando o favorito criado anteriormente...");
            entityManager.getTransaction().begin();
            
            favoritoDAO.excluir(favoritoRecuperado.getId());
            
            entityManager.getTransaction().commit();
            
            List<Favorito> favPostDelete = favoritoDAO.listar();
            System.out.println("\nListagem dos favoritos após o delete:");
            for(Favorito fav : favPostDelete)
            {
            	System.out.println(fav);
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
