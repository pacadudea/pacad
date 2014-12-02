package com.proint1.udea.produccion.ngc;

import java.util.List;

import com.proint1.udea.administracion.entidades.seguridad.Usuario;
import com.proint1.udea.produccion.util.ProduccionBLException;

/**
 * Interfaz que define los metodos que va a proveer el Servicio de Usuarios
 * 
 * @author  Edison - Juan
 *
 */
public interface UsuarioService {

	/**
	 * Obtiene una lista con todos los usuarios
	 * 
	 * @return
	 * @throws LicoreraDAOException
	 */
	public List<Usuario> listar() throws ProduccionBLException;

	
	/**
	 * Guarda un nuevo registro de usuario
	 * @param idPersona
	 * @param idEstado
	 * @param idPerfil
	 * @param usuario
	 * @param pass
	 * @param dtFechaExpiracion
	 * @param dtFechaUltimoingreso
	 * @return
	 * @throws ProduccionBLException
	 */
	public boolean insertarUsuario(long idPersona,
			String usuario, long usuarioActivo)
			throws ProduccionBLException;

	/**
	 * Actualiza un registro de usuario
	 * @param idUsuario
	 * @param idPersona
	 * @param idEstado
	 * @param idPerfil
	 * @param usuario
	 * @param pass
	 * @param dtFechaExpiracion
	 * @param dtFechaUltimoingreso
	 * @return
	 * @throws ProduccionBLException
	 */
	public boolean actualizarUsuario(long id, long idPersona,
			String usuario, long usuarioActivo)
			throws ProduccionBLException;
	/***
	 * 
	 * @param id : identificación del usuario a ser eliminado
	 * @return
	 * @throws ProduccionBLException
	 */
	public boolean eliminarUsuario(long id) throws ProduccionBLException;

	/**
	 * Encuentra un usuario por su id
	 * 
	 * @param pais
	 * @throws LicoreraDAOException
	 */
	public Usuario encontrarUsuario(long idUsuario) throws ProduccionBLException;
	/**
	 * Loguear un usuario al sistema
	 * @param usuario
	 * @param pass
	 * @return
	 * @throws LicoreraDAOException
	 */
	public Usuario loguearUsuario(String usuario, String pass)
			throws ProduccionBLException;
}
