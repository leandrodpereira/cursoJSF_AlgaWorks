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

import com.algaworks.pedidovenda.model.Cliente;
import com.algaworks.pedidovenda.repository.filter.ClienteFilter;
import com.algaworks.pedidovenda.service.NegocioException;
import com.algaworks.pedidovenda.util.jpa.Transactional;

public class Clientes implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Cliente guardar(Cliente cliente) {
		return manager.merge(cliente);
	}
	
	public Cliente porId(Long id) {
		return manager.find(Cliente.class, id);
	}
	
	@Transactional
	public void remover(Cliente cliente) {
		try {
			cliente = this.porId(cliente.getId());
			manager.remove(cliente);
			manager.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Não foi possível excluir: "+e.getMessage());
		}
	}
	
	
	public Cliente porDocReceita(String documentoReceitaFederal){
		try {
			return manager.createQuery("from Cliente where documentoReceitaFederal = :documentoReceitaFederal ",Cliente.class)
					.setParameter("documentoReceitaFederal", documentoReceitaFederal)
					.getSingleResult();
		}catch (NoResultException e) {
			return null;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Cliente> filtrados(ClienteFilter filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Cliente.class);
		
		if (StringUtils.isNotBlank(filtro.getDocumentoReceita())) {
			criteria.add(Restrictions.eq("documentoReceitaFederal", filtro.getDocumentoReceita()));
		}
		
		if (StringUtils.isNotBlank(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}
		
		return criteria.addOrder(Order.asc("nome")).list();
	}

}
