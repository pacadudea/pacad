package com.proint1.udea.produccion.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.proint1.udea.administracion.entidades.terceros.TbAdmPersona;
import com.proint1.udea.produccion.dao.GrupoInvestigacionDAO;
import com.proint1.udea.produccion.dto.MiembrosGrupoInvestigacion;
import com.proint1.udea.produccion.entidades.TbPrdAutor;
import com.proint1.udea.produccion.entidades.TbPrdGrupoinvestigacion;
import com.proint1.udea.produccion.entidades.TbPrdMiembrosxgrupo;
import com.proint1.udea.produccion.entidades.TbPrdMiembrosxgrupoId;
import com.proint1.udea.produccion.util.ProduccionDAOException;

public class GrupoInvestigacionDAOImpl extends HibernateDaoSupport implements GrupoInvestigacionDAO {

	
	/**
	 * Implementación para entregar la lista de todos los dispositivos existentes en la base de datos
	 * @return Lista de dispositivos
	 * @throws ProduccionDAOException, si ocurre problema obteniendo la lista de grupos
	 */
	@Override
	public List<TbPrdGrupoinvestigacion> listar() throws ProduccionDAOException {
		Session session = null;
		List<TbPrdGrupoinvestigacion> grupos = new ArrayList<TbPrdGrupoinvestigacion>();
		try {
			session = getSession(true);
			Criteria criteria = session.createCriteria(TbPrdGrupoinvestigacion.class);
			grupos = criteria.list();
		} catch (HibernateException e) {
			throw new ProduccionDAOException("No se pudo ejecutar la operacion DAO, para obtener la lista de grupos de investigacion " + e.getMessage());
		} finally {
			/*if (session != null)
				session.close();*/
		}
		return grupos;
	}
	
	/**
	 * Implementación para entregrar una lista de grupos de investigacion dependiendo de un nombre 
	 * @param nombre de los grupos de los cuales se espera obtener la lista
	 * @return Lista de grupos de investigacion
	 * @throws ProduccionDAOException, si ocurre problema obteniendo la lista de dispositivos
	 */
	@Override
	public List<TbPrdGrupoinvestigacion> obtenerGrupos(String nombre) throws ProduccionDAOException {
		Session session = null;
		List<TbPrdGrupoinvestigacion> grupos = new ArrayList<TbPrdGrupoinvestigacion>();
		
		try{
			session = getSession();
			
			Criteria criteria = session.createCriteria(TbPrdGrupoinvestigacion.class);
			criteria.add(Restrictions.like("vrNombre", "%"+nombre+"%"));
			
			grupos = criteria.list();
			
		}catch(HibernateException e){
			throw new ProduccionDAOException(e);
		}
		return grupos;
	}

	@Override
	public void insertarGrupoInvestigacion(TbPrdGrupoinvestigacion grupo) throws ProduccionDAOException {
		Session session = null;
		Transaction tx = null;

		try {
			session = getSession(true);

			tx = session.beginTransaction();
			session.save(grupo);
			tx.commit();
		} catch (HibernateException e) {
			throw new ProduccionDAOException(e);
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public void eliminarGrupoInvestigacion(TbPrdGrupoinvestigacion grupo) throws ProduccionDAOException {
		Session session = null;
		Transaction tx = null;

		try {
			session = getSession(true);

			tx = session.beginTransaction();
			session.delete(grupo);
			tx.commit();

		} catch (HibernateException e) {
			throw new ProduccionDAOException("No se pudo ejecutar la operacion DAO, eliminar");
		} finally {
			if (session != null)
				session.close();
		}
		
	}

	@Override
	public void editarGrupoInvestigacion(TbPrdGrupoinvestigacion grupo) throws ProduccionDAOException {
		Session session = null;
		Transaction tx = null;

		try {
			session = getSession(true);

			tx = session.beginTransaction();
			session.update(grupo);
			tx.commit();

		} catch (HibernateException e) {
			throw new ProduccionDAOException("No se pudo ejecutar la operacion DAO, editar");
		} finally {
			if (session != null)
				session.close();
		}
		
	}

	/**
	 * Elimina todos los integrantes de un grupo de investigacion
	 * @param grupo Instancia del Grupo a eliminar
	 * @throws ProduccionDAOException si ocurre un problema en la operación DAO de eliminar
	 */
	@Override
	public void eliminarIntegrantesGrupo(long grupo) throws ProduccionDAOException {
		Session session = null;
		try {
			session = getSession(true);

			Query q = session.createQuery("delete TbPrdMiembrosxgrupo where tbPrdGrupoinvestigacion.nbIdn = " + grupo);
			q.executeUpdate();
			
		} catch (HibernateException e) {
			throw new ProduccionDAOException(e);
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public void asignarNuevosIntegantes(long idGrupo,List<MiembrosGrupoInvestigacion> nuevosIntegrantes) throws ProduccionDAOException {
		Session session = null;
		Transaction tx = null;

		try {
			session = getSession(true);

			tx = session.beginTransaction();
			
			for (Iterator iterator = nuevosIntegrantes.iterator(); iterator.hasNext();) {
				MiembrosGrupoInvestigacion member = (MiembrosGrupoInvestigacion) iterator.next();
				TbPrdMiembrosxgrupoId nmId = new TbPrdMiembrosxgrupoId();
				nmId.setNbAutIdn(member.getIdAutor());
				nmId.setNbGpiIdn(idGrupo);
				TbPrdMiembrosxgrupo nm = new TbPrdMiembrosxgrupo();
				nm.setId(nmId);
				
				session.save(nm);
			}
			tx.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			//throw new ProduccionDAOException(e);
		} finally {
			if (session != null)
				session.close();
		}	
	}
}

	
	