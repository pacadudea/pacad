package com.proint1.udea.produccion.dao;

import java.util.List;

import com.proint1.udea.produccion.entidades.TbPrdCampos;
import com.proint1.udea.produccion.util.ProduccionDAOException;

public interface CamposDAO {
	
	/**
	 * Listar los campos de una produccion.
	 * @return lista de campos
	 * @throws ProduccionDAOException en caso de problemas
	 */
	public List<TbPrdCampos> listar()throws ProduccionDAOException;

	/**
	 * Inserta un registro en la tabla campos de la base de datos.
	 * @param campo a insertar
	 * @throws ProduccionDAOException
	 */
	public void insertar(TbPrdCampos campo)throws ProduccionDAOException;

	/**
	 * Actualiza la informacion de un campo 
	 * @param campo a actualizar
	 * @throws ProduccionDAOException
	 */
	public void editar(TbPrdCampos campo)throws ProduccionDAOException;

	/**
	 * Obtiene un campo de la base de datos.
	 * @param id del campo
	 * @return campo
	 * @throws ProduccionDAOException
	 */
	public TbPrdCampos obtener(long id)throws ProduccionDAOException;
}
