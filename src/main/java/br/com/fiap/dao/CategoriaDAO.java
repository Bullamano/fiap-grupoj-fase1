package br.com.fiap.dao;

import br.com.fiap.needhelpapp.model.Categoria;
import jakarta.persistence.EntityManager;

public class CategoriaDAO extends GenericDAO<Categoria, Integer> {
	
	public CategoriaDAO(EntityManager entityManager) {
		super(entityManager);
	}
	
}
