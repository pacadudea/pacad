package com.proint1.udea.produccion.dao;

import java.util.List;

import com.proint1.udea.administracion.entidades.terceros.TbAdmTipoIdentificacion;

public interface TipoIdentificacionDAO {
	
	public List<TbAdmTipoIdentificacion> listar();
	
	public void insertar(TbAdmTipoIdentificacion tipoIdentificacion);

	public TbAdmTipoIdentificacion obtener(long id);
}
