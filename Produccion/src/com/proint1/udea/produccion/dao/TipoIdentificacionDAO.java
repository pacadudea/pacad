package com.proint1.udea.produccion.dao;

import java.util.List;

import com.proint1.udea.administracion.entidades.terceros.TbAdmTipoIdentificacion;
import com.proint1.udea.produccion.util.ProduccionDAOException;

public interface TipoIdentificacionDAO {
	
	public List<TbAdmTipoIdentificacion> listar()throws ProduccionDAOException;
	
	public void insertar(TbAdmTipoIdentificacion tipoIdentificacion)throws ProduccionDAOException;

	public TbAdmTipoIdentificacion obtener(long id)throws ProduccionDAOException;
}
