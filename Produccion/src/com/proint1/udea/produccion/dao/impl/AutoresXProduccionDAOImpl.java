package com.proint1.udea.produccion.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.proint1.udea.produccion.dao.AutoresXProduccionDAO;
import com.proint1.udea.produccion.entidades.TbPrdAutoresxproduccion;
import com.proint1.udea.produccion.entidades.TbPrdGrupoinvestigacion;
import com.proint1.udea.produccion.util.ProduccionDAOException;

public class AutoresXProduccionDAOImpl extends HibernateDaoSupport implements AutoresXProduccionDAO {

	
	/**
	 * Obtener la lista de Autores por Producciones
	 */
	@Override
	public List<TbPrdAutoresxproduccion> listar(TbPrdGrupoinvestigacion grupo) throws ProduccionDAOException {
	
		Session session = null;
		List<TbPrdAutoresxproduccion> listaAutoresProd = new ArrayList<TbPrdAutoresxproduccion>();
		try {
			session = getSession(true);
			Criteria criteria = session.createCriteria(TbPrdAutoresxproduccion.class).add(Restrictions.eq("tbPrdGrupoinvestigacion", grupo.getNbIdn()));
	
			listaAutoresProd = criteria.list();
		} catch (HibernateException e) {
			throw new ProduccionDAOException("No se pudo ejecutar la operacion DAO, para obtener la lista de autores por produccion " + e.getMessage());
		} finally {
			if (session != null)
				session.close();
		}
		return listaAutoresProd;
		
	}

}
