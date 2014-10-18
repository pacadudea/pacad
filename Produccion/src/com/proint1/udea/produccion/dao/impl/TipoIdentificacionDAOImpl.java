package com.proint1.udea.produccion.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.proint1.udea.administracion.entidades.terceros.TbAdmTipoIdentificacion;
import com.proint1.udea.produccion.dao.TipoIdentificacionDAO;
import com.proint1.udea.produccion.entidades.TbPrdAutor;

public class TipoIdentificacionDAOImpl extends HibernateDaoSupport implements TipoIdentificacionDAO {

	@Override
	public List<TbAdmTipoIdentificacion> listar() {
		Session session = null;
		List<TbAdmTipoIdentificacion> tiposIdentificacion = new ArrayList<TbAdmTipoIdentificacion>();
		try {
			session = getSession(true);
			Criteria criteria = session.createCriteria(TbAdmTipoIdentificacion.class);
			tiposIdentificacion = criteria.list();
			for (Iterator iterator = tiposIdentificacion.iterator(); iterator.hasNext();) {
				TbAdmTipoIdentificacion tipoIdentificacion = (TbAdmTipoIdentificacion) iterator.next();
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
	public void insertar(TbAdmTipoIdentificacion tipoIdentificacion) {
		// TODO Auto-generated method stub

	}

	@Override
	public TbAdmTipoIdentificacion obtener(long id) {
		TbAdmTipoIdentificacion tipoId = null;
		Session session = null;
		try {
			session = getSession(true);
			Criteria criteria = session.createCriteria(TbAdmTipoIdentificacion.class).add(Restrictions.eq("nbIdn", id));
			tipoId = (TbAdmTipoIdentificacion) criteria.uniqueResult();
		} catch (HibernateException e) {
			System.out.println("Error: TipoIDDAO.obtener: "+e.getMessage());
		} finally {
			if (session != null)
				session.close();
		}
		return tipoId;
	}

}
