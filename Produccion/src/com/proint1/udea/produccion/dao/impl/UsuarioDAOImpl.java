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

import com.proint1.udea.administracion.entidades.seguridad.Usuario;
import com.proint1.udea.produccion.dao.UsuarioDAO;
import com.proint1.udea.produccion.util.ProduccionDAOException;

/**
 * Implementacion de las funcionalidades del DAO
 * 
 * @author JuanBar
 *
 */
public class UsuarioDAOImpl extends HibernateDaoSupport implements UsuarioDAO {

	private static Logger logger = Logger.getLogger(UsuarioDAOImpl.class);

	@Override
	public void insertarUsuario(Usuario usuario) throws ProduccionDAOException {
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession(true);
			tx = session.beginTransaction();
			session.save(usuario);
			tx.commit();
		} catch (HibernateException e) {

			System.out.println(e.getMessage());
			throw new ProduccionDAOException("No se pudo crear el registro de usuario");

		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public void eliminarUsuario(Usuario usuario) throws ProduccionDAOException {
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession(true);
			tx = session.beginTransaction();
			session.delete(usuario);
			tx.commit();
		} catch (HibernateException e) {
			throw new ProduccionDAOException(
					"No se pudo eliminar el registro de usuario");
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public void editarUsuario(Usuario usuario) throws ProduccionDAOException {
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession(true);
			tx = session.beginTransaction();
			session.update(usuario);
			tx.commit();
		} catch (HibernateException e) {
			throw new ProduccionDAOException(
					"No se pudo actualizar el registro de usuario");
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public List<Usuario> listar() throws ProduccionDAOException {
		Session session = null;
		List<Usuario> usuarioes = new ArrayList<Usuario>();
		try {
			session = getSession(true);
			Criteria criteria = session.createCriteria(Usuario.class);
			usuarioes = criteria.list();
		} catch (HibernateException e) { 
			throw new ProduccionDAOException("No se pudo traer la lista de usuarios");
		} finally {
			if (session != null)
				session.close();
		}
		return usuarioes;
	}

	@Override
	public Usuario obtenerUsuario(long idUsuario) throws ProduccionDAOException {
		Session session = null;
		Usuario usuario = null;
		try {
			session = getSession(true);
			Criteria criteria = session.createCriteria(Usuario.class).add(Restrictions.eq("nbUsuIdn", idUsuario));
			usuario = (Usuario) criteria.uniqueResult();
		} catch (HibernateException e) { 
			throw new ProduccionDAOException("No se pudo encontrar el usuario");
		} finally {
			if (session != null)
				session.close();
		}
		return usuario;
	}

	@Override
	public Usuario loguearUsuario(String usuario, String pass) throws ProduccionDAOException {
		Session session = null;
		Usuario usu = null;
		try {
			session = getSession(true);
			Criteria criteria = session.createCriteria(Usuario.class);
			criteria.add(Restrictions.eq("vrUsuLogin", usuario));
			criteria.add(Restrictions.eq("nbUsuIdn", Long.parseLong(pass)));
			usu = (Usuario) criteria.uniqueResult();
		} catch (HibernateException e) { 
			throw new ProduccionDAOException("No se pudo encontrar el usuario");
		} finally {
			if (session != null)
				session.close();
		}
		return usu;
	}
}
