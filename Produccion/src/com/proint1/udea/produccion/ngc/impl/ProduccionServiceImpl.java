package com.proint1.udea.produccion.ngc.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.proint1.udea.produccion.dao.ProduccionDAO;
import com.proint1.udea.produccion.entidades.TbPrdAutor;
import com.proint1.udea.produccion.entidades.TbPrdProduccion;
import com.proint1.udea.produccion.ngc.ProduccionService;
import com.proint1.udea.produccion.util.ProduccionBLException;

public class ProduccionServiceImpl implements ProduccionService {

	ProduccionDAO produccionDAO;

	private static Logger logger = Logger.getLogger(ProduccionServiceImpl.class);

	@Override
	public List<TbPrdProduccion> listar()throws ProduccionBLException {
		logger.info("Iniciando consulta de Producciones");
		List<TbPrdProduccion> lista=null;
		try{
			lista= produccionDAO.listar();
		}catch(Exception e){
			throw new ProduccionBLException(e);
		}
		return lista;
	}

	@Override
	public TbPrdProduccion obtener(long id)throws ProduccionBLException {
		TbPrdProduccion prod = null;
		try{
		prod = produccionDAO.obtener(id);
		}catch(Exception e){
			throw new ProduccionBLException(e);
		}
		return prod;
	}

	@Override
	public void insertar(String titulo, List<TbPrdAutor> autores,
			String palabrasClave, long tipoProduccion,
			HashMap<Long, String> informacion)throws ProduccionBLException {
		// TODO Auto-generated method stub
		//Realizar validaciones
	}

	public ProduccionDAO getProduccionDAO() {
		return produccionDAO;
	}

	public void setProduccionDAO(ProduccionDAO produccionDAO) {
		this.produccionDAO = produccionDAO;
	}

}