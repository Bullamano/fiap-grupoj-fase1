package br.com.fiap.dao;

import br.com.fiap.needhelpapp.model.Usuario;
import jakarta.persistence.EntityManager;

public class UsuarioDAO extends GenericDAO<Usuario, Integer> {
	
	public UsuarioDAO(EntityManager entityManager) {
		super(entityManager);
	}
}
