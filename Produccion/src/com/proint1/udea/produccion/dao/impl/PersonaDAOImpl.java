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

import com.proint1.udea.administracion.entidades.terceros.TbAdmPersona;
import com.proint1.udea.produccion.dao.PersonaDAO;
import com.proint1.udea.produccion.entidades.TbPrdAutor;
import com.proint1.udea.produccion.util.ProduccionDAOException;

public class PersonaDAOImpl  extends HibernateDaoSupport implements PersonaDAO {

	@Override
	public List<TbAdmPersona> listar() {
		 Session session = null;
		  List<TbAdmPersona> personas = new ArrayList<TbAdmPersona>();
		  try {
		   session = getSession(true);
		   Criteria criteria = session.createCriteria(TbAdmPersona.class);
		   personas = criteria.list();
		   for (Iterator iterator = personas.iterator(); iterator.hasNext();) {
			   TbAdmPersona tipoIdentificacion = (TbAdmPersona) iterator.next();
		   }
		  } catch (HibernateException e) { // throw new
		   //
		  } finally {
		   if (session != null)
		    session.close();
		  }
		  
		  return personas;
		
	}

	@Override
	public void insertar(TbAdmPersona persona) throws ProduccionDAOException {
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession(true);
			tx = session.beginTransaction();
			session.save(persona);
			tx.commit();
		} catch (HibernateException e) {
			System.out.println("Error: PersonaDAOImpl: " + e.getMessage());
			throw new ProduccionDAOException(e);
		} finally {
			if (session != null)
				session.close();
		}

	}

	@Override
	public TbAdmPersona buscarPersona(String identificacion) throws ProduccionDAOException {
		TbAdmPersona persona = null;
		Session session = null;
		try {
			session = getSession(true);
			Criteria criteria = session.createCriteria(TbAdmPersona.class).add(Restrictions.eq("vrIdentificacion", identificacion));
			persona = (TbAdmPersona) criteria.uniqueResult();
		} catch (HibernateException e) {
			throw new ProduccionDAOException("No se pudo obtener la persona de la base de datos");
		} finally {
			if (session != null)
				session.close();
		}
		return persona;
	}
	
	@Override
	public boolean actualizar(TbAdmPersona persona)throws ProduccionDAOException {
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession(true);
			tx = session.beginTransaction();
			session.update(persona);
			tx.commit();
			return true;
		} catch (HibernateException e) {			
			return false;
			
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public boolean eliminar(TbAdmPersona persona) throws ProduccionDAOException {
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession(true);
			tx = session.beginTransaction();
			session.delete(persona);
			tx.commit();
			return true;
		} catch (HibernateException e) {
			return false;
		} finally {
			if (session != null)
				session.close();
		}
	}

}
