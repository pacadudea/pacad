package com.proint1.udea.produccion.ngc.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.proint1.udea.administracion.entidades.terceros.TbAdmTipoIdentificacion;
import com.proint1.udea.produccion.dao.TipoIdentificacionDAO;
import com.proint1.udea.produccion.ngc.TipoIdentificacionService;

public class TipoIdentificacionServiceImpl implements TipoIdentificacionService {
	
	TipoIdentificacionDAO tipoIdentificacionDAO;

	
	private static Logger logger = Logger.getLogger(TipoIdentificacionServiceImpl.class);

	@Override
	public List<TbAdmTipoIdentificacion> listar() {
		logger.info("-- Iniciando consulta de tipos de identificacion");
		return tipoIdentificacionDAO.listar();
	}
	public TipoIdentificacionDAO getTipoIdentificacionDAO() {
		return tipoIdentificacionDAO;
	}

	public void setTipoIdentificacionDAO(TipoIdentificacionDAO tipoIdentificacionDAO) {
		this.tipoIdentificacionDAO = tipoIdentificacionDAO;
	}

}
