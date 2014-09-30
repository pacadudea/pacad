package com.proint1.udea.produccion.dao;

import java.util.List;

import com.proint1.udea.administracion.entidades.terceros.Pais;

public interface PaisDAO {

	public List<Pais> listar();

	public void insertar(Pais pais);
	
	public Pais obtener(long id);

}
