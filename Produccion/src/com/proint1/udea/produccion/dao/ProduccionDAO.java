package com.proint1.udea.produccion.dao;

import java.util.List;

import com.proint1.udea.produccion.entidades.TbPrdProduccion;
import com.proint1.udea.produccion.util.ProduccionDAOException;

public interface ProduccionDAO {
	public List<TbPrdProduccion> listar()throws ProduccionDAOException;

	public void insertar(TbPrdProduccion produccion)throws ProduccionDAOException;

	public void eliminar(TbPrdProduccion produccion)throws ProduccionDAOException;

	public void editar(TbPrdProduccion produccion)throws ProduccionDAOException;

	public TbPrdProduccion obtener(long id)throws ProduccionDAOException;
}