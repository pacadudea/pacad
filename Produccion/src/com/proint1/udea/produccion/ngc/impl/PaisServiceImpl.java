package com.proint1.udea.produccion.ngc.impl;

import java.util.List;

import org.apache.log4j.Logger;
import com.proint1.udea.administracion.entidades.terceros.Pais;
import com.proint1.udea.produccion.dao.PaisDAO;
import com.proint1.udea.produccion.ngc.PaisService;

public class PaisServiceImpl implements PaisService {
	
	PaisDAO paisDAO;

	private static Logger logger = Logger.getLogger(TipoIdentificacionServiceImpl.class);
	
	@Override
	public List<Pais> listar() {
		logger.info("-- Iniciando consulta de paises");
		return paisDAO.listar();
	}
	public PaisDAO getPaisDAO() {
		return paisDAO;
	}
	public void setPaisDAO(PaisDAO paisDAO) {
		this.paisDAO = paisDAO;
	}

}
