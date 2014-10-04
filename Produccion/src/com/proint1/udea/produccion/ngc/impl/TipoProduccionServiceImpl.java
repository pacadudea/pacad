package com.proint1.udea.produccion.ngc.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.proint1.udea.produccion.dao.TipoProduccionDAO;
import com.proint1.udea.produccion.entidades.TbPrdTipoproduccion;
import com.proint1.udea.produccion.entidades.TbPrdTipoproduccionesxcampo;
import com.proint1.udea.produccion.ngc.TipoProduccionService;
import com.proint1.udea.produccion.util.ProduccionBLException;
import com.proint1.udea.produccion.util.ProduccionDAOException;

public class TipoProduccionServiceImpl implements TipoProduccionService {
	
	private static Logger logger = Logger.getLogger(TipoProduccionServiceImpl.class);
	
	/**
	 * Beans DAO necesarios.
	 */
	TipoProduccionDAO tipoProduccionDAO;

	/**
	 * Metodos set y get para los beans
	 * 
	 */
	public TipoProduccionDAO getTipoProduccionDAO() {
		return tipoProduccionDAO;
	}
	public void setTipoProduccionDAO(TipoProduccionDAO tipoProduccionDAO) {
		this.tipoProduccionDAO = tipoProduccionDAO;
	}


	@Override
	public List<TbPrdTipoproduccion> listar()throws ProduccionBLException {
		
		try {
			logger.info("-- Iniciando consulta de Tipos de Producción");
			return tipoProduccionDAO.listar();
		} catch (ProduccionDAOException e) {
			throw new ProduccionBLException(e.getMessage());
		}
	}

	@Override
	public void insertar()throws ProduccionBLException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public TbPrdTipoproduccion obtener(long id)throws ProduccionBLException {
		try {
			return tipoProduccionDAO.obtener(id);
		} catch (ProduccionDAOException e) {
			throw new ProduccionBLException(e.getMessage());
		}
	}
	@Override
	public List<TbPrdTipoproduccionesxcampo> obtenerCamposXTipo(
			long idTipoProduccion) throws ProduccionDAOException {
		return tipoProduccionDAO.obtenerCamposXTipo(idTipoProduccion);
	}
	
	
}