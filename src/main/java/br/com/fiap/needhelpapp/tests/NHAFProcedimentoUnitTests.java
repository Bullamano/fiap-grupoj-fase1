package br.com.fiap.needhelpapp.tests;

import br.com.fiap.dao.PaginaDAO;
import br.com.fiap.dao.ProcedimentoDAO;
import br.com.fiap.needhelpapp.model.Categoria;
import br.com.fiap.needhelpapp.model.Pagina;
import br.com.fiap.needhelpapp.model.Procedimento;
import br.com.fiap.utils.Enums;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

/**
 * Testes unitários para Procedimento.
 * Os testes seguem a ordem numérica testXN,
 * na qual X é uma letra (começando sem letra, seguido por A, B e assim por diante)
 * e N é o número ascendente do teste (1 a 9).
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NHAFProcedimentoUnitTests {
	
	
	//Variáveis a serem usadas nos testes
	static List<Procedimento> listagemProcedimento, procsPag, procedimentoByTitulo,
							  procedimentoByTituloPartial, procedimentosPostInsert, procedimentosPostDelete;
	static Pagina paginaPesquisada, paginaProcNovo, pagProcExistente;
	static Procedimento procedimento, procedimentoByTituloUnique, procedimentoCreate,
						procedimentoExistente, procedimentoRecuperado, procedimentoParaDeletar;
	
	EntityManager entityManager = Persistence.createEntityManagerFactory("need-help-app-fiap").createEntityManager();
	
	PaginaDAO paginaDAO = new PaginaDAO(entityManager);
	ProcedimentoDAO procedimentoDAO = new ProcedimentoDAO(entityManager);
	
	/**
	 * Teste de listagem de procedimentos
	 */
	@Test
	public void test1ListagemProcedimentos(){
		/**
		* Listagem de todas as entradas no banco de dados
		*/
		listagemProcedimento = procedimentoDAO.listar();
	    
	    /**
         * Testes
         */
		Assert.assertEquals(8, listagemProcedimento.size());
	}
	
	/**
	 * Teste de listagem de procedimentos por pagina
	 */
	@Test
	public void test2ListagemProcedimentoPorPagina(){
		/**
	     * Listagem de todos os procedimentos de uma página específica
	     */
	    paginaPesquisada = paginaDAO.recuperar(2);
	    procsPag = procedimentoDAO.getByPagina(paginaPesquisada, Enums.order.irrelevant);
	    
	    /**
         * Testes
         */
		Assert.assertEquals(2, procsPag.size());
		Assert.assertEquals("3", procsPag.get(0).getId().toString());
	}
	
	/**
	 * Teste de pesquisa de procedimento por titulo
	 */
	@Test
	public void test3ProcedimentoGetByTitulo(){
		/**
         * Pesquisa por uma string nome de uma pagina (retorno lista)
         */
        procedimentoByTitulo = procedimentoDAO.getByTitulo("Ingredientes");
        
        /**
         * Testes
         */
		Assert.assertEquals("1 - Ingredientes - 3 xícaras de arroz cru, 2 concha de feijão cozido...", procedimentoByTitulo.get(0).toString());
	}
	
	/**
	 * Teste de pesquisa de procedimento por titulo único
	 */
	@Test
	public void test4ProcedimentoGetByTituloUnique(){
		/**
         * Pesquisa por uma string nome de uma pagina (retorno único)
         */  
		procedimentoByTituloUnique = procedimentoDAO.getByTituloUnique("Top 10 lugares");
        
        /**
         * Testes
         */
		Assert.assertEquals("Top 10 lugares", procedimentoByTituloUnique.getTitulo());
	}
	
	/**
	 * Teste de pesquisa de procedimento por titulo parcial
	 */
	@Test
	public void test5ProcedimentoGetByTituloPartial(){
		/**
         * Pesquisa por uma string parcial do nome de uma página
         */
		procedimentoByTituloPartial = procedimentoDAO.getByTituloPartial("P");
        
        /**
         * Testes
         */
		Assert.assertEquals(6, procedimentoByTituloPartial.size());
		Assert.assertEquals("2 - Preparo - Coloque o óleo, a cebola e o alho...", procedimentoByTituloPartial.get(0).toString());
	}
	
	/**
	 * Teste de inserção de objeto
	 */
	@Test
	public void test6ProcedimentoCreate() {
		/**
         * Criação de objeto no banco
         */
		entityManager.getTransaction().begin();
        
        procedimentoCreate = new Procedimento();
        procedimentoCreate.setTitulo("Como voar");          
        procedimentoCreate.setOrdem(1);
        procedimentoCreate.setTarefas("Se jogue no chão e erre.");
        paginaProcNovo = paginaDAO.recuperar(1);
        procedimentoCreate.setPagina(paginaProcNovo);
        
        procedimentoDAO.salvar(procedimentoCreate);
        
        entityManager.getTransaction().commit();
        
        procedimentosPostInsert = procedimentoDAO.listar();
		
        /**
         * Testes
         */
		Assert.assertNotNull(procedimentoCreate);
		Assert.assertNotNull(procedimentoCreate.getId());
		Assert.assertEquals("Como voar", procedimentoCreate.getTitulo());
		Assert.assertEquals("1", procedimentoCreate.getOrdem().toString());
		Assert.assertEquals("Se jogue no chão e erre.", procedimentoCreate.getTarefas());
		Assert.assertEquals(9, procedimentosPostInsert.size());
	}
	
	/**
	 * Teste de modificação de objeto
	 */
	@Test
	public void test7ProcedimentoUpdate() {		
		/**
         * Modificando um objeto já presente no banco
         */
		entityManager.getTransaction().begin();
        
        procedimentoExistente = new Procedimento();
        procedimentoExistente = procedimentoDAO.getByTituloUnique("Como voar");         
        
        procedimentoExistente.setOrdem(2);
        procedimentoExistente.setTitulo("Como voltar no tempo");
        procedimentoExistente.setTarefas("Passo 1: compre um Delorean...");
        
        pagProcExistente = paginaDAO.recuperar(2);
        procedimentoExistente.setPagina(pagProcExistente);
        
        procedimentoDAO.salvar(procedimentoExistente);
        
        entityManager.getTransaction().commit();
        
        procedimentoRecuperado = procedimentoDAO.recuperar(procedimentoExistente.getId());
        
        /**
         * Testes
         */
		Assert.assertNotNull(procedimentoExistente);
		Assert.assertNotNull(procedimentoRecuperado);
		Assert.assertSame(procedimentoExistente, procedimentoRecuperado);
		Assert.assertNotNull(procedimentoRecuperado.getId());
		Assert.assertEquals("Como voltar no tempo", procedimentoRecuperado.getTitulo());
		Assert.assertEquals("Passo 1: compre um Delorean...", procedimentoRecuperado.getTarefas());
		Assert.assertEquals(pagProcExistente, procedimentoRecuperado.getPagina());
		Assert.assertEquals("2", procedimentoRecuperado.getOrdem().toString());
	}
	
	/**
	 * Teste deletando objeto
	 */
	@Test
	public void test8ProcedimentoDelete() {		
		/**
         * Deletando o objeto criado acima
         */
		entityManager.getTransaction().begin();
        
        procedimentoDAO.excluir(procedimentoRecuperado.getId());
        
        entityManager.getTransaction().commit();
        
        procedimentosPostDelete = procedimentoDAO.listar();
        
        /**
         * Testes
         */
        Assert.assertEquals(8, procedimentosPostDelete.size());
	}
}
