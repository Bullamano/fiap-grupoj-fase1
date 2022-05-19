package br.com.fiap.needhelpapp.tests;

import br.com.fiap.dao.PaginaDAO;
import br.com.fiap.dao.RecursosDAO;
import br.com.fiap.needhelpapp.model.Pagina;
import br.com.fiap.needhelpapp.model.Recursos;
import br.com.fiap.utils.Enums;

import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

/**
 * Testes unitários para Recursos.
 * Os testes seguem a ordem numérica testXN,
 * na qual X é uma letra (começando sem letra, seguido por A, B e assim por diante)
 * e N é o número ascendente do teste (1 a 9).
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NHAFRecursosUnitTests {
	
	//Variáveis a serem usadas nos testes
	static List<Recursos> listagemRecursos, recursosPag, recursosByLink,
						  recursosByLinkPartial, recursosPostInsert, recursosExistentesList, recursosPostDelete;
	static Recursos recursosCreate, recursosExistentes, recursosRecuperados;
	static Pagina paginaPesquisada, paginaRecCreate;
	
	EntityManager entityManager = Persistence.createEntityManagerFactory("need-help-app-fiap").createEntityManager();
	
	PaginaDAO paginaDAO = new PaginaDAO(entityManager);
	RecursosDAO recursosDAO = new RecursosDAO(entityManager);
	
	/**
	 * Teste de listagem de recursos
	 */
	@Test
	public void test1ListagemRecursos(){
		/**
		* Listagem de todas as entradas no banco de dados
		*/
		listagemRecursos = recursosDAO.listar();
	    
	    /**
         * Testes
         */
		Assert.assertEquals(2, listagemRecursos.size());
	}
	
	/**
	 * Teste de listagem de recursos por página
	 */
	@Test
	public void test2ListagemRecursosPorPagina(){
		/**
	     * Listagem de todos os recursos de uma página específica
	     */
	    paginaPesquisada = paginaDAO.recuperar(4);
	    recursosPag = recursosDAO.getByPagina(paginaPesquisada, Enums.order.irrelevant);
	    
	    /**
         * Testes
         */
	    Assert.assertEquals(1, recursosPag.size());
		Assert.assertEquals("2", recursosPag.get(0).getId().toString());
	}
	
	/**
	 * Teste de pesquisa de recursos por link
	 */
	@Test
	public void test3RecursosGetByLink(){
		/**
         * Pesquisa por uma string link de um recurso (retorno lista)
         */
		recursosByLink = recursosDAO.getByLink("https://www.masp.org.br/", Enums.linkType.leitura);
	    
	    /**
         * Testes
         */
		Assert.assertEquals(
				"1 - Página: Lugares para se visitar em São Paulo - Leitura: https://www.masp.org.br/ - Imagem: https://imagens.ebc.com.br/ZnNy-PMxXCSQ8ig6dLoAkuhwmb0=/1170x700/smart/https://agenciabrasil.ebc.com.br/sites/default/files/thumbnails/image/avenida_paulista_rvsa_081220202132.jpg?itok=KuTl0h-D",
				recursosByLink.get(0).toString());
	}
	
	/**
	 * Teste de pesquisa de recursos por link
	 */
	@Test
	public void test4RecursosGetByLinkPartial(){
		/**
         * Pesquisa por uma string parcial do link de um recurso
         */
        recursosByLinkPartial = recursosDAO.getByLinkPartial("youtube", Enums.linkType.video);
	    
	    /**
         * Testes
         */
		Assert.assertEquals(
				"2 - Página: Nós de gravata - Vídeo: https://www.youtube.com/watch?v=xAg7z6u4NE8&ab_channel=tiehole - Leitura: https://www.wikihow.com/Tie-a-Windsor-Knot",
				recursosByLinkPartial.get(0).toString());
	}
	
	/**
	 * Teste de inserção de objeto
	 */
	@Test
	public void test5RecursosCreate() {
		/**
         * Criação de objeto no banco
         * Obs.: Os recursos podem conter um dos links ou todos, no caso de links relacionados.
         * Neste caso, contudo, para fins demonstrativos, colocamos todos os links, mesmo eles
         * não tendo relações entre si (aparentemente).
         */
        entityManager.getTransaction().begin();
        
        recursosCreate = new Recursos();
        recursosCreate.setLinkVideo("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
        recursosCreate.setLinkImagem("https://i.pinimg.com/550x/a4/be/38/a4be386e607995393685aa0d69034b94.jpg");
        recursosCreate.setLinkLeitura("https://www.wikihow.com/Calculate-Pi-by-Throwing-Frozen-Hot-Dogs");
        recursosCreate.setOrdem(1);
        
        paginaRecCreate = paginaDAO.recuperar(1);
        recursosCreate.setPagina(paginaRecCreate);

        recursosDAO.salvar(recursosCreate);
        
        entityManager.getTransaction().commit();
        
        recursosPostInsert = recursosDAO.listar();
        		
        /**
         * Testes
         */
		Assert.assertNotNull(recursosCreate);
		Assert.assertNotNull(recursosCreate.getId());
		Assert.assertEquals("https://www.youtube.com/watch?v=dQw4w9WgXcQ", recursosCreate.getLinkVideo());
		Assert.assertEquals("https://i.pinimg.com/550x/a4/be/38/a4be386e607995393685aa0d69034b94.jpg", recursosCreate.getLinkImagem());
		Assert.assertEquals("https://www.wikihow.com/Calculate-Pi-by-Throwing-Frozen-Hot-Dogs", recursosCreate.getLinkLeitura());
		Assert.assertEquals(paginaRecCreate, recursosCreate.getPagina());
		Assert.assertEquals("1", recursosCreate.getOrdem().toString());
		Assert.assertEquals(3, recursosPostInsert.size());
	}
	
	/**
	 * Teste de modificação de objeto
	 */
	@Test
	public void test6RecursosUpdate() {		
		/**
         * Modificando um objeto já presente no banco
         */
		entityManager.getTransaction().begin();
        
        recursosExistentesList = recursosDAO.getByLink(recursosCreate.getLinkVideo(), Enums.linkType.video);
        recursosExistentes = recursosExistentesList.get(0);
        
        recursosExistentes.setLinkVideo("https://www.youtube.com/watch?v=hpjV962DLWs");
        
        recursosDAO.salvar(recursosExistentes);
        
        entityManager.getTransaction().commit();
        
        recursosRecuperados = recursosDAO.recuperar(recursosExistentes.getId());
        
        /**
         * Testes
         */
		Assert.assertNotNull(recursosExistentes);
		Assert.assertNotNull(recursosRecuperados);
		Assert.assertSame(recursosExistentes, recursosRecuperados);
		Assert.assertNotNull(recursosRecuperados.getId());
		Assert.assertEquals("https://www.youtube.com/watch?v=hpjV962DLWs", recursosRecuperados.getLinkVideo());
	}
	
	/**
	 * Teste deletando objeto
	 */
	@Test
	public void test7RecursosDelete() {		
		/**
         * Deletando o objeto criado acima
         */
		entityManager.getTransaction().begin();
        
        recursosDAO.excluir(recursosRecuperados.getId());
        
        entityManager.getTransaction().commit();
        
        recursosPostDelete = recursosDAO.listar();
        
        /**
         * Testes
         */
        Assert.assertEquals(2, recursosPostDelete.size());
	}
}
