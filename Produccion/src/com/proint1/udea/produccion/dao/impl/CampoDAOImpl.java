package com.proint1.udea.produccion.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.proint1.udea.produccion.dao.CampoDAO;
import com.proint1.udea.produccion.entidades.TbPrdCampo;
import com.proint1.udea.produccion.util.ProduccionDAOException;

public class CampoDAOImpl extends HibernateDaoSupport implements CampoDAO {

	@Override
	public List<TbPrdCampo> listar()throws ProduccionDAOException{
		Session session = null;
		List<TbPrdCampo> campos = new ArrayList<TbPrdCampo>();
		try {
			session = getSession(true);
			Criteria criteria = session.createCriteria(TbPrdCampo.class);
			campos = criteria.list();
			for (Iterator iterator = campos.iterator(); iterator.hasNext();) {
				TbPrdCampo tbPrdAutor = (TbPrdCampo) iterator.next();
			}
		} catch (HibernateException e) { // throw new
			throw new ProduccionDAOException("No se pudo listar los campos desde la base de datos.");
		} finally {
			if (session != null)
				session.close();
		}
		return campos;
	}

	@Override
	public void insertar(TbPrdCampo campo)throws ProduccionDAOException{
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

	@Override
	public void editar(TbPrdCampo campo)throws ProduccionDAOException{
		// TODO Auto-generated method stub

	}

	@Override
	public TbPrdCampo obtener(TbPrdCampo campo)throws ProduccionDAOException{
		// TODO Auto-generated method stub
		return null;
	}

}
