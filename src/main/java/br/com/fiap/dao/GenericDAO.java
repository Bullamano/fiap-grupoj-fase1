package br.com.fiap.dao;

import java.util.List;
import jakarta.persistence.EntityManager;
import java.lang.reflect.ParameterizedType;
import jakarta.persistence.criteria.CriteriaQuery;

/**
 * Classe generica para persistencia de dados.
 * @author Bullamano
 *
 * @param <Entidade> Representa um objeto que possui um equivalente no banco de dados.
 * @param <Chave> Representa a chave (ID) relacionada ao objeto Entidade.
 */
public abstract class GenericDAO<Entidade,Chave> {
	
	protected Class<Entidade> classeEntidade;	
	protected EntityManager em;
	
	/**
	 * Construtor da classe GenericDAO.
	 * @param em Entity Manager
	 */
	@SuppressWarnings("unchecked")
	public GenericDAO(EntityManager em) {
        this.em = em;
        this.classeEntidade = (Class<Entidade>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	/**
	 * Salva o objeto desejado como um registro no banco.
	 * @param entidade Objeto a ser salvo.
	 */
	public void salvar(Entidade entidade) {
		this.em.persist(entidade);
	}
	
	/**
	 * Procura e retorna um registro.
	 * @param chave Chave (ID) associado a um registro.
	 * @return Um objeto relacionado ao registro desejado.
	 */
	public Entidade recuperar(Chave chave) {
		return this.em.find(classeEntidade, chave);
	}
	
	/**
	 * Lista todos os registros que forem encontrados.
	 * @return Uma lista com todos os objetos encontrados.
	 */
	@SuppressWarnings("unused")
	public List<Entidade> listar() {
		CriteriaQuery<Entidade> query = this.em.getCriteriaBuilder().createQuery(this.classeEntidade);
		CriteriaQuery<Entidade> select = query.select(query.from(this.classeEntidade));
		 
		return this.em.createQuery(query.select(query.from(this.classeEntidade))).getResultList();
	}
	
	/**
	 * Exclui um registro especifico.
	 * @param chave Chave (ID) do banco de um registro.
	 */
	public void excluir(Chave chave) {
		Entidade entidadeExcluir = this.recuperar(chave);
		
		if (entidadeExcluir == null) {
			throw new IllegalArgumentException(
					"Nenhum registro de " + this.classeEntidade.getSimpleName() + "encontrado para a chave " + chave);
		}
		
		this.em.remove(entidadeExcluir);
	}
	
}

