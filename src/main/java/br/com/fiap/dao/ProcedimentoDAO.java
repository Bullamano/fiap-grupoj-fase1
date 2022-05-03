package br.com.fiap.dao;

import br.com.fiap.needhelpapp.model.Procedimento;
import jakarta.persistence.EntityManager;

public class ProcedimentoDAO extends GenericDAO<Procedimento, Integer> {
	
	public ProcedimentoDAO(EntityManager entityManager) {
		super(entityManager);
	}

}
