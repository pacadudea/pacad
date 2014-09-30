package com.proint1.udea.produccion.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.proint1.udea.produccion.dao.AutorDAO;
import com.proint1.udea.produccion.entidades.TbPrdAutor;
import com.proint1.udea.produccion.util.ProduccionDAOException;

public class AutorDAOImpl extends HibernateDaoSupport implements AutorDAO {

	private static Logger logger = Logger.getLogger(AutorDAOImpl.class);

	/**
	 * Implementación para entregar la lista de todos los autores existentes en la base de datos
	 * @return Lista con todos los autores en la base de datos
	 * @throws ProduccionDAOException en caso de ocurrir errores en la operacion DAO de seleccion
	 */
	@Override
	public List<TbPrdAutor> listar() throws ProduccionDAOException {
		Session session = null;
		List<TbPrdAutor> autores = new ArrayList<TbPrdAutor>();
		try {
			session = getSession(true);
			Criteria criteria = session.createCriteria(TbPrdAutor.class);
			autores = criteria.list();
		} catch (HibernateException e) { // throw new
			throw new ProduccionDAOException("No se pudieron obtener los autores de la base de datos");
		} finally {
			if (session != null)
				session.close();
		}
		return autores;
	}

	@Override
	public void insertarAutor(TbPrdAutor autor)throws ProduccionDAOException {
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession(true);
			tx = session.beginTransaction();
			session.save(autor);
			tx.commit();
		} catch (HibernateException e) {
			
			System.out.println(e.getMessage());
			throw new ProduccionDAOException("No se pudieron insertar autores de la base de datos");
			

		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public void eliminarAutor(TbPrdAutor autor)throws ProduccionDAOException {
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession(true);
			tx = session.beginTransaction();
			session.delete(autor);
			tx.commit();
		} catch (HibernateException e) {
			throw new ProduccionDAOException("No se pudieron eliminar autores de la base de datos");
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public void editarAutor(TbPrdAutor autor)throws ProduccionDAOException {
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession(true);
			tx = session.beginTransaction();
			session.update(autor);
			tx.commit();
		} catch (HibernateException e) {
			throw new ProduccionDAOException("No se pudieron editar autores de la base de datos");
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public TbPrdAutor obtenerAutor(long id)throws ProduccionDAOException {
		TbPrdAutor autor2 = null;
		Session session = null;
		try {
			session = getSession(true);
			Criteria criteria = session.createCriteria(TbPrdAutor.class).add(Restrictions.eq("id", id));
			autor2 = (TbPrdAutor) criteria.uniqueResult();
		} catch (HibernateException e) {
			throw new ProduccionDAOException("No se pudo obtener autor de la base de datos");
		} finally {
			if (session != null)
				session.close();
		}
		return autor2;
	}
}
