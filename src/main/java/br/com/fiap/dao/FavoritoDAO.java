package br.com.fiap.dao;

import br.com.fiap.needhelpapp.model.Favorito;
import br.com.fiap.needhelpapp.model.Usuario;
import jakarta.persistence.EntityManager;

public class FavoritoDAO extends GenericDAO<Favorito, Integer> {
	
	public FavoritoDAO(EntityManager entityManager) {
		super(entityManager);
	}
	
	public void excluirByUsuarioId(Usuario usuario) {
		this.em.createQuery(
				"delete from Favorito e where e.usuario = :usuario"
				).setParameter("usuario", usuario);
	}
}
