package com.proint1.udea.produccion.ngc.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.proint1.udea.produccion.dao.ProduccionDAO;
import com.proint1.udea.produccion.entidades.TbPrdAutor;
import com.proint1.udea.produccion.entidades.TbPrdProduccion;
import com.proint1.udea.produccion.ngc.ProduccionService;

public class ProduccionServiceImpl implements ProduccionService {

	ProduccionDAO produccionDAO;

	private static Logger logger = Logger
			.getLogger(ProduccionServiceImpl.class);

	@Override
	public List<TbPrdProduccion> listar() {
		logger.info("Iniciando consulta de Producciones");
		return produccionDAO.listar();
	}

	@Override
	public TbPrdProduccion obtener(long id) {
		return produccionDAO.obtener(id);
	}

	@Override
	public void insertar(String titulo, List<TbPrdAutor> autores,
			String palabrasClave, long tipoProduccion,
			HashMap<Long, String> informacion) {
		// TODO Auto-generated method stub
	}

	public ProduccionDAO getProduccionDAO() {
		return produccionDAO;
	}

	public void setProduccionDAO(ProduccionDAO produccionDAO) {
		this.produccionDAO = produccionDAO;
	}

}