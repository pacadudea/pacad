package com.proint1.udea.produccion.ngc.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.proint1.udea.administracion.entidades.terceros.TbAdmPaises;
import com.proint1.udea.produccion.dao.PaisDAO;
import com.proint1.udea.produccion.ngc.PaisService;
import com.proint1.udea.produccion.util.ProduccionBLException;
import com.proint1.udea.produccion.util.ProduccionDAOException;

public class PaisServiceImpl implements PaisService {
	
	PaisDAO paisDAO;

	private static Logger logger = Logger.getLogger(TipoIdentificacionServiceImpl.class);
	
	@Override
	public List<TbAdmPaises> listar() throws ProduccionBLException {
		logger.info("-- Iniciando consulta de paises");
		List<TbAdmPaises> lista = null;
		try{
			lista = paisDAO.listar();
		}catch(Exception e){
			throw new ProduccionBLException(e);
		}
		return lista;
	}
	
	
	public PaisDAO getPaisDAO() {
		return paisDAO;
	}
	public void setPaisDAO(PaisDAO paisDAO) {
		this.paisDAO = paisDAO;
	}

}
