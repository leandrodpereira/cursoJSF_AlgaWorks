package com.algaworks.pedidovenda.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.algaworks.pedidovenda.model.Usuario;
import com.algaworks.pedidovenda.service.NegocioException;
import com.algaworks.pedidovenda.util.jpa.Transactional;

public class Usuarios implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;

	public Usuario guardar(Usuario usuario) {
		return manager.merge(usuario);
	}
	
	public Usuario porId(Long id) {
		return manager.find(Usuario.class, id);
	}
	
	@Transactional
	public void remover(Usuario usuario) {
		try {
			usuario = this.porId(usuario.getId());
			manager.remove(usuario);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Não foi possível excluir: "+e.getMessage());
		}
	}
	
	public Usuario porEmail(String email){
		try {
			return manager.createQuery("from Usuario where upper(email) = :email ",Usuario.class)
					.setParameter("email", email)
					.getSingleResult();
		}catch (NoResultException e) {
			return null;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> filtrados (String nome){
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Usuario.class);
		
		if(StringUtils.isNotBlank(nome)) {
			return criteria.add(Restrictions.ilike("nome", nome, MatchMode.ANYWHERE)).addOrder(Order.asc("id")).list();
		}
		
		return null;
	}
}
