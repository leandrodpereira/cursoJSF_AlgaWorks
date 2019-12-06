package com.algaworks.pedidovenda.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.algaworks.pedidovenda.model.Grupo;
import com.algaworks.pedidovenda.model.Usuario;

public class Grupos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public List<Grupo> mostraTrodos(){
		return manager.createQuery("from Grupo",Grupo.class).getResultList();
	}
	
	public Grupo porId(Long id) {
		return manager.find(Grupo.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Grupo> porUsuario(Usuario usuario){
		return manager.createQuery("select u.grupos from Usuario u where u.id = :id")
				.setParameter("id", usuario.getId())
				.getResultList();
	}
}
