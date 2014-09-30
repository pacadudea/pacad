package com.proint1.udea.produccion.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.proint1.udea.produccion.dao.ProduccionDAO;
import com.proint1.udea.produccion.dao.TipoProduccionDAO;
import com.proint1.udea.produccion.entidades.TbPrdAutor;
import com.proint1.udea.produccion.entidades.TbPrdProduccion;
import com.proint1.udea.produccion.entidades.TbPrdTipoproduccion;

public class ProduccionDAOImpl extends HibernateDaoSupport implements ProduccionDAO {

	private static Logger logger=Logger.getLogger(ProduccionDAOImpl.class);

	public List<TbPrdProduccion> listar() {
		Session session = null;
		List<TbPrdProduccion> producciones = new ArrayList<TbPrdProduccion>();
		
		try {
			session = getSession(true);
			Criteria criteria = session.createCriteria(TbPrdProduccion.class);
			producciones = criteria.list();
		} catch (HibernateException e) { // throw new
			System.out.println(e.getMessage());
		} finally {
			if (session != null)
				session.close();
		}
		return producciones;
	}

	@Override
	public void insertar(TbPrdProduccion produccion) {
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession(true);
			tx = session.beginTransaction();
			session.save(produccion);
			tx.commit();
		} catch (HibernateException e) {
			//throw new SolicitudesDAOException(e);
		} finally {
			if (session != null)
				session.close();
		}

	}

	@Override
	public TbPrdProduccion obtener(long id) {
		TbPrdProduccion produccion = null;
		Session session = null;
		try {
			session = getSession(true);
			Criteria criteria = session.createCriteria(TbPrdProduccion.class).add(Restrictions.eq("nbIdn", id));
			produccion = (TbPrdProduccion) criteria.uniqueResult();
		} catch (HibernateException e) {
			// throw new SolicitudesDAOException(e);
		} finally {
			if (session != null)
				session.close();
		}
		return produccion;
	}

	@Override
	public void eliminar(TbPrdProduccion produccion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editar(TbPrdProduccion produccion) {
		// TODO Auto-generated method stub
		
	}
}