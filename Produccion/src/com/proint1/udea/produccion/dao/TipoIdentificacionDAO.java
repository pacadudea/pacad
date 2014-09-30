package com.proint1.udea.produccion.dao;

import java.util.List;

import com.proint1.udea.administracion.entidades.terceros.TipoIdentificacion;

public interface TipoIdentificacionDAO {
	
	public List<TipoIdentificacion> listar();
	
	public void insertar(TipoIdentificacion tipoIdentificacion);

	public TipoIdentificacion obtener(long id);
}
