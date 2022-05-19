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
        	
        	//DAO para os objetos Categoria e Pagina
        	CategoriaDAO categoriaDAO = new CategoriaDAO(entityManager);
        	PaginaDAO paginaDAO = new PaginaDAO(entityManager);
        	ProcedimentoDAO procedimentoDAO = new ProcedimentoDAO(entityManager);
        	RecursosDAO recursosDAO = new RecursosDAO(entityManager);
        	FavoritoDAO favoritoDAO = new FavoritoDAO(entityManager);
        	UsuarioDAO usuarioDAO = new UsuarioDAO(entityManager);

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
             * Pesquisa por uma string parcial do nome do autor de uma página
             */            
            System.out.println("\nPesquisa por nome do autor (parcial):");
            System.out.println(paginaDAO.getByNomeAutor("1"));
            
            /**
             * Pesquisa por um ID do usuário autor de uma página
             */            
            System.out.println("\nPesquisa por ID do autor (parcial):");
            System.out.println(paginaDAO.getByIdAutor(2));
            
            /**
             * Criação de objeto no banco
             */
            System.out.println("\nCriando uma nova página...");
            entityManager.getTransaction().begin();
            
            Pagina paginaNova = new Pagina();
            paginaNova.setNome("Página inútil");
            Categoria categPagNova = categoriaDAO.recuperar(1);
            paginaNova.setCategoria(categPagNova);
            
            Usuario usuarioPagNova = usuarioDAO.recuperar(1);
            paginaNova.setAutor(usuarioPagNova);
            
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
            
            Usuario usuarioPagExistente = usuarioDAO.recuperar(2);
            
            paginaExistente.setNome("Página maravilhosa");
            paginaExistente.setProcedimentos(procedimentosCollection);
            paginaExistente.setRecursos(recursosCollection);
            paginaExistente.setFavoritos(favoritoCollection);
            paginaExistente.setAutor(usuarioPagExistente);
            
            paginaDAO.salvar(paginaExistente);
            
            entityManager.getTransaction().commit();
            
            Pagina paginaRecuperada = paginaDAO.recuperar(paginaExistente.getId());
            
            System.out.println("");
            System.out.println("\nA página foi modificada: " + paginaRecuperada);
            System.out.println("\nE agora possui:");
            System.out.println("\n\tProcedimentos: " + paginaRecuperada.getProcedimentos());
            System.out.println("\n\tRecursos: " + paginaRecuperada.getRecursos());
            System.out.println("\n\tFavoritos: " + paginaRecuperada.getFavoritos());
            System.out.println("\n\tAutor: " + paginaRecuperada.getAutor(entityManager));
            
            /**
             * Deletando o objeto criado acima
             */
            System.out.println("\nDeletando a página criada anteriormente...");
            entityManager.getTransaction().begin();
                        
            paginaDAO.excluir(paginaRecuperada);
            
            entityManager.getTransaction().commit();
            
            List<Pagina> pagPostDelete = paginaDAO.listar();
            System.out.println("\nListagem das páginas após o delete:");
            for(Pagina pag : pagPostDelete)
            {
            	System.out.println(pag);
            }
            
            /**
             * Criando uma página e deletando seu usuário
             * (páginas devem ser mantidas mesmo após a exclusão de seu autor)
             */
            //Criação
            entityManager.getTransaction().begin();
            
            Usuario usuarioPagEterna = new Usuario();
            usuarioPagEterna.setLogin("User volátil");
            usuarioPagEterna.setSenha("senhaSecreta");
            usuarioPagEterna.setEmail("email@email.com");
            
            usuarioDAO.salvar(usuarioPagEterna);
            
            entityManager.getTransaction().commit();
            
            //Salvamento da Pagina separadamente
            //(usar mais de um salvar por Transaction pode causar erros)
            entityManager.getTransaction().begin();
            
            Pagina paginaEterna = new Pagina();
            paginaEterna.setNome("Página eterna");
            
            Categoria categPagEterna = categoriaDAO.recuperar(1);
            paginaEterna.setCategoria(categPagEterna);
            
            paginaEterna.setAutor(usuarioPagEterna);
            
            paginaDAO.salvar(paginaEterna);
            
            entityManager.getTransaction().commit();
            
            System.out.println("A página " + paginaEterna + " foi criada!");
            System.out.println("O autor da página criada é: " + paginaEterna.getAutor(entityManager));
            
            //Exclusão do usuário
            entityManager.getTransaction().begin();
            
            usuarioDAO.excluir(usuarioPagEterna);
            
            entityManager.getTransaction().commit();
            
            //A página deve ser mantida mesmo o usuário não existindo mais
            Pagina paginaSemUser = new Pagina();
            paginaSemUser = paginaDAO.getByNameUnique(paginaEterna.getNome());
            System.out.println("A página " + paginaSemUser + " ainda existe!");
            System.out.println("O ID original do usuário autor é " + paginaSemUser.getIdAutor());
            System.out.println("O nome do usuário autor é " + paginaSemUser.getNomeAutor());
            System.out.println("Essas informações continuam no banco mesmo o usuário tendo sido excluído --> " + usuarioDAO.recuperar(usuarioPagEterna.getId()));
            
            //Exclusão da página para retornar o banco ao estado original
            entityManager.getTransaction().begin();
            
            paginaDAO.excluir(paginaSemUser);
            
            entityManager.getTransaction().commit();
            
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
