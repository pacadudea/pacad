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

import com.proint1.udea.produccion.dao.CamposDAO;
import com.proint1.udea.produccion.entidades.TbPrdAutor;
import com.proint1.udea.produccion.entidades.TbPrdCampos;
import com.proint1.udea.produccion.util.ProduccionDAOException;

public class CamposDAOImpl extends HibernateDaoSupport implements CamposDAO {

	public List<TbPrdCampos> listar()throws ProduccionDAOException{
		Session session = null;
		List<TbPrdCampos> campos = new ArrayList<TbPrdCampos>();
		try {
			session = getSession(true);
			Criteria criteria = session.createCriteria(TbPrdCampos.class);
			campos = criteria.list();
			for (Iterator iterator = campos.iterator(); iterator.hasNext();) {
				TbPrdCampos tbPrdAutor = (TbPrdCampos) iterator.next();
			}
		} catch (HibernateException e) { // throw new
			throw new ProduccionDAOException("No se pudo listar los campos desde la base de datos.");
		} finally {
			if (session != null)
				session.close();
		}
		return campos;
	}

	public void insertar(TbPrdCampos campo)throws ProduccionDAOException{
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession(true);
			tx = session.beginTransaction();
			session.save(campo);
			tx.commit();
		} catch (HibernateException e) {
			throw new ProduccionDAOException("No se pudo insertar los campos desde la base de datos.");
		} finally {
			if (session != null)
				session.close();
		}
	}

	public void editar(TbPrdCampos campo)throws ProduccionDAOException{
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession(true);
			tx = session.beginTransaction();
			session.update(campo);
			tx.commit();
		} catch (HibernateException e) {
			throw new ProduccionDAOException("No se pudo insertar los campos desde la base de datos.");
		} finally {
			if (session != null)
				session.close();
		}

	}

	public TbPrdCampos obtener(long id)throws ProduccionDAOException{
		TbPrdCampos campo2 = null;
		Session session = null;
		try{
			session =getSession(true);
			Criteria criteria = session.createCriteria(TbPrdCampos.class).add(Restrictions.eq("nbIdn", id));
			campo2 = (TbPrdCampos) criteria.uniqueResult();
		} catch (HibernateException e) {
			throw new ProduccionDAOException("No se pudo obtener los campos desde la base de datos.");
		} finally {
			if (session != null)
				session.close();
		}
		return campo2;
	}

}
