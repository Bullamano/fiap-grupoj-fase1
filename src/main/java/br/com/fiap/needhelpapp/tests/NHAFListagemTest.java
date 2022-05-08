package br.com.fiap.needhelpapp.tests;

import java.util.List;
import br.com.fiap.dao.*;
import br.com.fiap.needhelpapp.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

/**
 * Teste para listagem de tudo referente aos objetos cadastrado no banco de dados
 */
public class NHAFListagemTest {
	
	public static void main(String[] args) {
		
		EntityManager entityManager = null;
        try 
        {
        	//Entity manager
        	entityManager =  Persistence.
                createEntityManagerFactory("fiap-grupoj-atividade-fase1").
                createEntityManager();
        	
        	//Instâncias da DAO de cada objeto
        	CategoriaDAO categoriaDAO = new CategoriaDAO(entityManager);
        	PaginaDAO paginaDAO = new PaginaDAO(entityManager);
        	ProcedimentoDAO procedimentoDAO = new ProcedimentoDAO(entityManager);
            RecursosDAO recursosDAO = new RecursosDAO(entityManager);
            UsuarioDAO usuarioDAO = new UsuarioDAO(entityManager);
            FavoritoDAO favoritoDAO = new FavoritoDAO(entityManager);

            /**
             * Pesquisa de cada objetoDAO no banco
             */
            List<Categoria> listaCat = categoriaDAO.listar();
            List<Pagina> listaPag = paginaDAO.listar();
            List<Procedimento> listaProc = procedimentoDAO.listar();
            List<Recursos> listaRec = recursosDAO.listar();
            List<Usuario> listaUsr = usuarioDAO.listar();
            List<Favorito> listaFav = favoritoDAO.listar();
            
            /**
             * Apresantação no console do que foi recebido do banco
             */
            System.out.println("");
            System.out.println("--Categorias--");
            for(Categoria cat : listaCat)
            {
            	System.out.println(cat);
            }            
            System.out.println("");
            System.out.println("--Páginas--");
            for(Pagina pag : listaPag)
            {
            	System.out.println(pag);//.getCategoria());
            }            
            System.out.println("");
            System.out.println("--Procedimentos--");
            for(Procedimento proc : listaProc)
            {
            	System.out.println(proc);
            }            
            System.out.println("");
            System.out.println("--Recursos--");
            for(Recursos rec : listaRec)
            {
            	System.out.println(rec);
            }            
            System.out.println("");
            System.out.println("--Usuários--");
            for(Usuario usr : listaUsr)
            {
            	System.out.println(usr);
            }            
            System.out.println("");
            System.out.println("--Favoritos--");
            for(Favorito fav : listaFav)
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
