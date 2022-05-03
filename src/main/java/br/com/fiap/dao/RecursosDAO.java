package br.com.fiap.dao;

import br.com.fiap.needhelpapp.model.Recursos;
import jakarta.persistence.EntityManager;

public class RecursosDAO extends GenericDAO<Recursos, Integer> {
	
	public RecursosDAO(EntityManager entityManager) {
		super(entityManager);
	}

}
