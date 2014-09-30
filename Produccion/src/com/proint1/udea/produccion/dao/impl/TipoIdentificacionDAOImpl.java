package com.proint1.udea.produccion.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.proint1.udea.administracion.entidades.terceros.TipoIdentificacion;
import com.proint1.udea.produccion.dao.TipoIdentificacionDAO;
import com.proint1.udea.produccion.entidades.TbPrdAutor;

public class TipoIdentificacionDAOImpl extends HibernateDaoSupport implements TipoIdentificacionDAO {

	@Override
	public List<TipoIdentificacion> listar() {
		Session session = null;
		List<TipoIdentificacion> tiposIdentificacion = new ArrayList<TipoIdentificacion>();
		try {
			session = getSession(true);
			Criteria criteria = session.createCriteria(TipoIdentificacion.class);
			tiposIdentificacion = criteria.list();
			for (Iterator iterator = tiposIdentificacion.iterator(); iterator.hasNext();) {
				TipoIdentificacion tipoIdentificacion = (TipoIdentificacion) iterator.next();
			}
		} catch (HibernateException e) { // throw new
			//
		} finally {
			if (session != null)
				session.close();
		}
		return tiposIdentificacion;
	}

	@Override
	public void insertar(TipoIdentificacion tipoIdentificacion) {
		// TODO Auto-generated method stub

	}

	@Override
	public TipoIdentificacion obtener(long id) {
		TipoIdentificacion tipoId = null;
		Session session = null;
		try {
			session = getSession(true);
			Criteria criteria = session.createCriteria(TipoIdentificacion.class).add(Restrictions.eq("idn", id));
			tipoId = (TipoIdentificacion) criteria.uniqueResult();
		} catch (HibernateException e) {
			System.out.println("Error: TipoIDDAO.obtener: "+e.getMessage());
		} finally {
			if (session != null)
				session.close();
		}
		return tipoId;
	}

}
