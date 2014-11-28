package com.proint1.udea.produccion.dao;

import java.util.List;

import com.proint1.udea.produccion.entidades.TbPrdCampo;
import com.proint1.udea.produccion.util.ProduccionDAOException;

public interface CampoDAO {
	
	public List<TbPrdCampo> listar()throws ProduccionDAOException;

	public void insertar(TbPrdCampo campo)throws ProduccionDAOException;

	public void editar(TbPrdCampo campo)throws ProduccionDAOException;

	public TbPrdCampo obtener(TbPrdCampo campo)throws ProduccionDAOException;
}
