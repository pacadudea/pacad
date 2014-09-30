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

import com.proint1.udea.administracion.entidades.terceros.Persona;
import com.proint1.udea.produccion.dao.PersonaDAO;
import com.proint1.udea.produccion.entidades.TbPrdGrupoinvestigacion;
import com.proint1.udea.produccion.util.ProduccionDAOException;

public class PersonaDAOImpl  extends HibernateDaoSupport implements PersonaDAO {

	@Override
	public List<Persona> listar() {
		 Session session = null;
		  List<Persona> personas = new ArrayList<Persona>();
		  try {
		   session = getSession(true);
		   Criteria criteria = session.createCriteria(Persona.class);
		   personas = criteria.list();
		   for (Iterator iterator = personas.iterator(); iterator.hasNext();) {
		    Persona tipoIdentificacion = (Persona) iterator.next();
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
	public void insertar(Persona persona) throws ProduccionDAOException {
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

}
