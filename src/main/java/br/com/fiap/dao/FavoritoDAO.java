package br.com.fiap.dao;

import br.com.fiap.needhelpapp.model.Favorito;
import jakarta.persistence.EntityManager;

public class FavoritoDAO extends GenericDAO<Favorito, Integer> {
	
	public FavoritoDAO(EntityManager entityManager) {
		super(entityManager);
	}
}
