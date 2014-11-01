package com.proint1.udea.produccion.dao;

import java.util.List;

import com.proint1.udea.administracion.entidades.terceros.TbAdmPaises;
import com.proint1.udea.produccion.util.ProduccionDAOException;

public interface PaisDAO {

	public List<TbAdmPaises> listar()throws ProduccionDAOException;

	public void insertar(TbAdmPaises pais)throws ProduccionDAOException;
	
	public TbAdmPaises obtener(long id)throws ProduccionDAOException;

}
