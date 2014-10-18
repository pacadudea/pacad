package com.proint1.udea.produccion.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.proint1.udea.administracion.entidades.terceros.TbAdmPaises;
import com.proint1.udea.produccion.dao.PaisDAO;

public class PaisDAOImpl  extends HibernateDaoSupport implements PaisDAO {

	@Override
	public List<TbAdmPaises> listar() {
		Session session = null;
		List<TbAdmPaises> paises = new ArrayList<TbAdmPaises>();
		try {
			session = getSession(true);
			Criteria criteria = session.createCriteria(TbAdmPaises.class);
			paises = criteria.list();
			for (Iterator iterator = paises.iterator(); iterator.hasNext();) {
				TbAdmPaises pais = (TbAdmPaises) iterator.next();
			}
		} catch (HibernateException e) { // throw new
			//
		} finally {
			if (session != null)
				session.close();
		}
		return paises;
	}

	@Override
	public void insertar(TbAdmPaises pais) {
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession(true);
			tx = session.beginTransaction();
			session.save(pais);
			tx.commit();
		} catch (HibernateException e) {
			//throw new SolicitudesDAOException(e);
		} finally {
			if (session != null)
				session.close();
		}

	}

	@Override
	public TbAdmPaises obtener(long id) {
		TbAdmPaises  pais = null;
		Session session = null;
		try {
			session = getSession(true);
			Criteria criteria = session.createCriteria(TbAdmPaises.class).add(Restrictions.eq("nbIdn", id));
			pais = (TbAdmPaises) criteria.uniqueResult();
		} catch (HibernateException e) {
			System.out.println("Error: Pais.obtener: "+e.getMessage());
		} finally {
			if (session != null)
				session.close();
		}
		return pais;
	}

}
