package com.proint1.udea.produccion.ngc.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.proint1.udea.produccion.dao.CampoDAO;
import com.proint1.udea.produccion.entidades.TbPrdCampo;
import com.proint1.udea.produccion.ngc.CampoService;
import com.proint1.udea.produccion.util.ProduccionBLException;
import com.proint1.udea.produccion.util.ProduccionDAOException;

public class CampoServiceImpl implements CampoService {
	
	private static final Logger logger = Logger.getLogger(CampoServiceImpl.class);
	
	CampoDAO campoDAO;

	@Override
	public List<TbPrdCampo> listar()throws ProduccionBLException  {
		List<TbPrdCampo> lista = null;
		try {
			lista=campoDAO.listar();
		}catch (ProduccionDAOException e){
			throw new ProduccionBLException(e.getMessage());
		}
		return lista;
	}

	@Override
	public void insertar(String descripcion, boolean estado)throws ProduccionBLException {
		try {
			campoDAO.insertar(new TbPrdCampo(descripcion, estado ? '1' : '0', "1",new Date()));
		} catch (ProduccionDAOException e) {
			throw new ProduccionBLException(e.getMessage());
		}
	}

	@Override
	public TbPrdCampo obtener(long id)throws ProduccionBLException{
		// TODO Auto-generated method stub
		return null;
	}

	public CampoDAO getCampoDAO() {
		return campoDAO;
	}

	public void setCampoDAO(CampoDAO campoDAO) {
		this.campoDAO = campoDAO;
	}

}
