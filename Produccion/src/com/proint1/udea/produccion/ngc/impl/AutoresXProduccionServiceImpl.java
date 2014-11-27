package com.proint1.udea.produccion.ngc.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.proint1.udea.produccion.dao.AutoresXProduccionDAO;
import com.proint1.udea.produccion.entidades.TbPrdAutoresxproduccion;
import com.proint1.udea.produccion.entidades.TbPrdGrupoinvestigacion;
import com.proint1.udea.produccion.util.ProduccionBLException;
import com.proint1.udea.produccion.util.ProduccionDAOException;

public class AutoresXProduccionServiceImpl implements AutoresXProduccionDAO {

	private static final Logger logger = Logger.getLogger(AutoresXProduccionServiceImpl.class);
	
	AutoresXProduccionDAO autoresXProduccionDAO;
	
	
	@Override
	public List<TbPrdAutoresxproduccion> listar(TbPrdGrupoinvestigacion grupo)throws ProduccionBLException{
		logger.info("-- Iniciando consulta de AutoresXProduccion");
		List<TbPrdAutoresxproduccion> lista= null;
		
		try {
			lista = autoresXProduccionDAO.listar(grupo);
		}catch (ProduccionDAOException e) {
			throw new ProduccionBLException("No se pudo obtener la lista de grupos de investigacion. " + e.getMessage());
		}
				
		return lista;
	}


	public AutoresXProduccionDAO getAutoresXProduccionDAO() {
		return autoresXProduccionDAO;
	}


	public void setAutoresXProduccionDAO(AutoresXProduccionDAO autoresXProduccionDAO) {
		this.autoresXProduccionDAO = autoresXProduccionDAO;
	}
	
	
	
}
