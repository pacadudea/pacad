package com.proint1.udea.produccion.dao;

import java.util.List;

import com.proint1.udea.produccion.entidades.TbPrdProduccion;

public interface ProduccionDAO {
	public List<TbPrdProduccion> listar();

	public void insertar(TbPrdProduccion produccion);

	public void eliminar(TbPrdProduccion produccion);

	public void editar(TbPrdProduccion produccion);

	public TbPrdProduccion obtener(long id);
}