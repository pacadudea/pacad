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

import com.proint1.udea.administracion.entidades.terceros.Pais;
import com.proint1.udea.administracion.entidades.terceros.TipoIdentificacion;
import com.proint1.udea.produccion.dao.PaisDAO;
import com.proint1.udea.produccion.entidades.TbPrdAutor;

public class PaisDAOImpl  extends HibernateDaoSupport implements PaisDAO {

	@Override
	public List<Pais> listar() {
		Session session = null;
		List<Pais> paises = new ArrayList<Pais>();
		try {
			session = getSession(true);
			Criteria criteria = session.createCriteria(Pais.class);
			paises = criteria.list();
			for (Iterator iterator = paises.iterator(); iterator.hasNext();) {
				Pais pais = (Pais) iterator.next();
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
	public void insertar(Pais pais) {
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
	public Pais obtener(long id) {
		Pais  pais = null;
		Session session = null;
		try {
			session = getSession(true);
			Criteria criteria = session.createCriteria(Pais.class).add(Restrictions.eq("nbIdn", id));
			pais = (Pais) criteria.uniqueResult();
		} catch (HibernateException e) {
			System.out.println("Error: Pais.obtener: "+e.getMessage());
		} finally {
			if (session != null)
				session.close();
		}
		return pais;
	}

}
