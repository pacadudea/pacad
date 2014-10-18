package com.proint1.udea.produccion.dao;

import java.util.List;

import com.proint1.udea.administracion.entidades.terceros.TbAdmPaises;

public interface PaisDAO {

	public List<TbAdmPaises> listar();

	public void insertar(TbAdmPaises pais);
	
	public TbAdmPaises obtener(long id);

}
