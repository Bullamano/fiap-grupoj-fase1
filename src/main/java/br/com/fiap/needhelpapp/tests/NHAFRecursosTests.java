package br.com.fiap.needhelpapp.tests;

import java.util.List;

import br.com.fiap.dao.PaginaDAO;
import br.com.fiap.dao.RecursosDAO;
import br.com.fiap.needhelpapp.model.Pagina;
import br.com.fiap.needhelpapp.model.Recursos;
import br.com.fiap.utils.Enums;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

/**
 * Testes referentes a Recursos
 */
public class NHAFRecursosTests {
	
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
        	RecursosDAO recursosDAO = new RecursosDAO(entityManager);

        	/**
        	 * Listagem de todas as entradas no banco de dados
        	 */
            List<Recursos> testeRec = recursosDAO.listar();
            System.out.println("\nListagem de todos os recursos:");
            for(Recursos rec : testeRec)
            {
            	System.out.println(rec);
            }
            
            /**
             * Listagem de todos os recursos por página
             */
            List<Pagina> listaPag = paginaDAO.listar();
            System.out.println("\nListagem de todos os recursos por página:");
            for(Pagina pag : listaPag){
            	System.out.println(pag);
            	List<Recursos> listaRec = recursosDAO.getByPagina(entityManager, pag.getId(), Enums.order.desc);
            	for(Recursos rec : listaRec){
                	System.out.println("\n\t" + rec);
                }
            }
            
            /**
             * Listagem de todos os recursos de uma página específica
             */
            System.out.println("\nListagem de todos os recursos de uma página específica:");
            Pagina paginaPesquisada = paginaDAO.recuperar(4);
            List<Recursos> recsPag = recursosDAO.getByPagina(paginaPesquisada, Enums.order.irrelevant);
        	for(Recursos rec : recsPag){
            	System.out.println(rec);
            }
            
            /**
             * Pesquisa por uma string link de um recurso (retorno lista)
             */            
            System.out.println("\nPesquisa por nome (lista):");
            System.out.println(recursosDAO.getByLink("https://www.masp.org.br/", Enums.linkType.leitura));
            
            
            /**
             * Pesquisa por uma string parcial do link de um recurso
             */            
            System.out.println("\nPesquisa por nome (parcial):");
            System.out.println(recursosDAO.getByLinkPartial("youtube", Enums.linkType.video));
            
            /**
             * Criação de objeto no banco
             * Obs.: Os recursos podem conter um dos links ou todos, no caso de links relacionados.
             * Neste caso, contudo, para fins demonstrativos, colocamos todos os links, mesmo eles
             * não tendo relações entre si (aparentemente).
             */
            entityManager.getTransaction().begin();
            
            Recursos recursosNovos = new Recursos();
            recursosNovos.setLinkVideo("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
            recursosNovos.setLinkImagem("https://i.pinimg.com/550x/a4/be/38/a4be386e607995393685aa0d69034b94.jpg");
            recursosNovos.setLinkLeitura("https://www.wikihow.com/Calculate-Pi-by-Throwing-Frozen-Hot-Dogs");
            recursosNovos.setOrdem(1);
            
            Pagina pagRecNovo = paginaDAO.recuperar(1);
            recursosNovos.setPagina(pagRecNovo);

            recursosDAO.salvar(recursosNovos);
            
            entityManager.getTransaction().commit();
            
            System.out.println("\nPesquisando os novos recursos:");
            System.out.println(recursosDAO.getByLink(recursosNovos.getLinkImagem(), Enums.linkType.imagem));
            List<Recursos> recPostInsert = recursosDAO.listar();
            System.out.println("\nListagem dos recursos após o insert:");
            for(Recursos rec : recPostInsert)
            {
            	System.out.println(rec);
            }
            
            /**
             * Modificando um objeto já presente no banco
             */
            entityManager.getTransaction().begin();
            
            List<Recursos> recursosExistentesList = recursosDAO.getByLink(recursosNovos.getLinkVideo(), Enums.linkType.video);
            Recursos recursosExistentes = recursosExistentesList.get(0);
            System.out.println("\nOs recursos " + recursosExistentes + " serã modificados!\n");
            
            recursosExistentes.setLinkVideo("https://www.youtube.com/watch?v=hpjV962DLWs");
            
            recursosDAO.salvar(recursosExistentes);
            
            entityManager.getTransaction().commit();
            
            Recursos recursosRecuperados = recursosDAO.recuperar(recursosExistentes.getId());
            System.out.println("");
            System.out.println("\nOs recursos foram modificados: " + recursosRecuperados);
            
            /**
             * Deletando o objeto criado acima
             */
            System.out.println("\nDeletando os recursos criados anteriormente...");
            entityManager.getTransaction().begin();
            
            recursosDAO.excluir(recursosRecuperados.getId());
            
            entityManager.getTransaction().commit();
            
            List<Recursos> recPostDelete = recursosDAO.listar();
            System.out.println("\nListagem dos recursos após o delete:");
            for(Recursos rec : recPostDelete)
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
