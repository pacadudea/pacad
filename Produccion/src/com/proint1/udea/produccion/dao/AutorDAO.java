package com.proint1.udea.produccion.dao;

import java.util.List;
import com.proint1.udea.produccion.entidades.TbPrdAutor;
import com.proint1.udea.produccion.util.ProduccionDAOException;

public interface AutorDAO {
	
	/**
	 * Implementación para entregar la lista de todos los autores existentes en la base de datos
	 * @return Lista con todos los autores en la base de datos
	 * @throws ProduccionDAOException en caso de ocurrir errores en la operacion DAO de seleccion
	 */
	public List<TbPrdAutor> listar() throws ProduccionDAOException;

	/**
	 * Insertar un nuevo autor
	 * @param autor
	 */
	public void insertarAutor(TbPrdAutor autor)throws ProduccionDAOException;

	/**
	 * Eliminar un autor de la base de datos.
	 * @param autor
	 */
	public void eliminarAutor(TbPrdAutor autor)throws ProduccionDAOException;
	
	/**
	 * Editar un autor existente en la base de datos.
	 * @param autor
	 */
	public void editarAutor(TbPrdAutor autor)throws ProduccionDAOException;

	/**
	 * Obtener un autor de la base de datos
	 * @param long id del autor 
 	 * @return TbPrdAutor
	 */
	public TbPrdAutor obtenerAutor(long id)throws ProduccionDAOException;
	
}
