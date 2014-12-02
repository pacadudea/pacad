package com.proint1.udea.produccion.dao;

import java.util.List;

import com.proint1.udea.administracion.entidades.seguridad.Usuario;
import com.proint1.udea.produccion.util.ProduccionDAOException;

public interface UsuarioDAO {
	
	/**
	 * Implementación para entregar la lista de todos los Usuarioes existentes en la base de datos
	 * @return Lista con todos los Usuarioes en la base de datos
	 * @throws ProduccionDAOException en caso de ocurrir errores en la operacion DAO de seleccion
	 */
	public List<Usuario> listar() throws ProduccionDAOException;

	/**
	 * Insertar un nuevo Usuario
	 * @param Usuario
	 */
	public void insertarUsuario(Usuario usuario)throws ProduccionDAOException;

	/**
	 * Eliminar un Usuario de la base de datos.
	 * @param Usuario
	 */
	public void eliminarUsuario(Usuario usuario)throws ProduccionDAOException;
	
	/**
	 * Editar un Usuario existente en la base de datos.
	 * @param Usuario
	 */
	public void editarUsuario(Usuario usuario)throws ProduccionDAOException;

	/**
	 * Obtener un Usuario de la base de datos
	 * @param long id del Usuario 
 	 * @return Usuario
	 */
	public Usuario obtenerUsuario(long id)throws ProduccionDAOException;
	
	/**
	 * Loguear un usuario al sistema
	 * @param pais
	 * @throws LicoreraDAOException
	 */
	public Usuario loguearUsuario(String usuario,String pass)throws ProduccionDAOException;
}
