package br.com.fiap.dao;

import br.com.fiap.needhelpapp.model.Pagina;
import jakarta.persistence.EntityManager;

public class PaginaDAO extends GenericDAO<Pagina, Integer> {
	
	public PaginaDAO(EntityManager entityManager) {
		super(entityManager);
	}

}
