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

import com.proint1.udea.produccion.dao.TipoProduccionDAO;
import com.proint1.udea.produccion.entidades.TbPrdTipoproduccion;
import com.proint1.udea.produccion.entidades.TbPrdTipoproduccionesxcampo;
import com.proint1.udea.produccion.util.ProduccionDAOException;

public class TipoProduccionDAOImpl extends HibernateDaoSupport implements TipoProduccionDAO {

	private static Logger logger=Logger.getLogger(TipoProduccionDAOImpl.class);

	@Override
	public List<TbPrdTipoproduccion> listar()throws ProduccionDAOException{
		Session session = null;
		List<TbPrdTipoproduccion> tiposProduccion = new ArrayList<TbPrdTipoproduccion>();
		
		try {
			session = getSession(true);
			Criteria criteria = session.createCriteria(TbPrdTipoproduccion.class);
			tiposProduccion = criteria.list();
		} catch (HibernateException e) { 
			throw new ProduccionDAOException("No se pudieron obtener los tiposProduccion de la base de datos" +e.getMessage());
		} finally {
			if (session != null)
				session.close();
		}
		return tiposProduccion;
	}

	@Override
	public void insertar(TbPrdTipoproduccion tipoProduccion)throws ProduccionDAOException{
		Session session = null;
		Transaction tx = null;
		try {
			session = getSession(true);
			tx = session.beginTransaction();
			session.save(tipoProduccion);
			tx.commit();
		} catch (HibernateException e) {
			throw new ProduccionDAOException("No se pudieron obtener los tiposProduccion de la base de datos"+e.getMessage());
		} finally {
			if (session != null)
				session.close();
		}
	}

	@Override
	public void eliminar(TbPrdTipoproduccion tipoProduccion)throws ProduccionDAOException{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editar(TbPrdTipoproduccion tipoProduccion)throws ProduccionDAOException{
		// TODO Auto-generated method stub
		
	}

	@Override
	public TbPrdTipoproduccion obtener(long id) throws ProduccionDAOException{
		TbPrdTipoproduccion tipoProduccion = null;
		Session session = null;
		try {
			session = getSession(true);
			Criteria criteria = session.createCriteria(TbPrdTipoproduccion.class).add(Restrictions.eq("nbIdn", id));
			tipoProduccion = (TbPrdTipoproduccion) criteria.uniqueResult();
		} catch (HibernateException e) {
			throw new ProduccionDAOException("No se pudieron obtener los tiposProduccion de la base de datos"+e.getMessage());
		} finally {
			if (session != null)
				session.close();
		}
		return tipoProduccion;
	}

	@Override
	public List<TbPrdTipoproduccionesxcampo> obtenerCamposXTipo(
			long idTipoProduccion)  throws ProduccionDAOException {
		Session session = null;
		List<TbPrdTipoproduccionesxcampo> tiposProduccion = new ArrayList<TbPrdTipoproduccionesxcampo>();
		TbPrdTipoproduccion tipo = new TbPrdTipoproduccion();
		tipo.setNbIdn(idTipoProduccion);
		
		try {
			session = getSession(true);
			Criteria criteria = session.createCriteria(TbPrdTipoproduccionesxcampo.class).add(Restrictions.eq("nbTipoproduccion", tipo));
			tiposProduccion = criteria.list();
		} catch (HibernateException e) { 
			throw new ProduccionDAOException("No se pudieron obtener los campos por tiposProduccion de la base de datos: " +e.getMessage());
		} finally {
			if (session != null)
				session.close();
		}
		return tiposProduccion;
	}

}