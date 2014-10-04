package com.proint1.udea.produccion.ngc.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.proint1.udea.administracion.entidades.terceros.TbAdmPaises;
import com.proint1.udea.administracion.entidades.terceros.TbAdmPersona;
import com.proint1.udea.administracion.entidades.terceros.TipoIdentificacion;
import com.proint1.udea.produccion.dao.AutorDAO;
import com.proint1.udea.produccion.dao.PaisDAO;
import com.proint1.udea.produccion.dao.PersonaDAO;
import com.proint1.udea.produccion.dao.TipoIdentificacionDAO;
import com.proint1.udea.produccion.entidades.TbPrdAutor;
import com.proint1.udea.produccion.ngc.AutorService;
import com.proint1.udea.produccion.util.ProduccionBLException;
import com.proint1.udea.produccion.util.ProduccionDAOException;
import com.proint1.udea.produccion.util.Validaciones;

public class AutorServiceImpl implements AutorService {

	AutorDAO autorDAO; // Atributo que vincula con la capa
	TipoIdentificacionDAO tipoIdentificacionDAO;
	PersonaDAO personaDAO;
	PaisDAO paisDAO;

	public TipoIdentificacionDAO getTipoIdentificacionDAO() {
		return tipoIdentificacionDAO;
	}

	public void setTipoIdentificacionDAO(
			TipoIdentificacionDAO tipoIdentificacionDAO) {
		this.tipoIdentificacionDAO = tipoIdentificacionDAO;
	}

	public PersonaDAO getPersonaDAO() {
		return personaDAO;
	}

	public void setPersonaDAO(PersonaDAO personaDAO) {
		this.personaDAO = personaDAO;
	}

	public PaisDAO getPaisDAO() {
		return paisDAO;
	}

	public void setPaisDAO(PaisDAO paisDAO) {
		this.paisDAO = paisDAO;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		AutorServiceImpl.logger = logger;
	}

	private static Logger logger = Logger.getLogger(AutorServiceImpl.class);

	public AutorDAO getAutorDAO() {
		return autorDAO;
	}

	public void setAutorDAO(AutorDAO autorDAO) {
		this.autorDAO = autorDAO;
	}

	/**
	 * Implementación para entregar la lista de todos los autores existentes en
	 * la base de datos
	 * 
	 * @return Lista con todos los autores en la base de datos
	 * @throws ProduccionDAOException
	 *             en caso de ocurrir errores en la operacion DAO de seleccion
	 */
	@Override
	public List<TbPrdAutor> listar() throws ProduccionBLException {
		logger.info("-- Iniciando consulta de autores");
		try {
			return autorDAO.listar();
		} catch (ProduccionDAOException e) {
			throw new ProduccionBLException(e.getMessage());
		}
	}

	@Override
	public void insertar(long tipoIdentificacionIdn, String id, String nombres,
			String apellidos, String direccion, String telefono, String email,
			String sexo, Date fechaNacimiento, long nacionalidad)
			throws ProduccionBLException {

		/**
		 * iniciamos validaciones antes de grabar el usuario
		 */

		if (Validaciones.isTextoVacio(sexo)) {
			throw new ProduccionBLException(
					"El sexo del autor no puede ser nulo, ni una cadena de caracteres vacio");
		}
		if (Validaciones.isTextoVacio(email)) {
			throw new ProduccionBLException(
					"El email del autor no puede ser nulo, ni una cadena de caracteres vacio");
		}
		if(!Validaciones.isEmail(email)){
			throw new ProduccionBLException("El correo electronico del cleinte debe ser valido");
		}
		if (Validaciones.isTextoVacio(telefono)) {
			throw new ProduccionBLException(
					"El telefono del autor no puede ser nulo, ni una cadena de caracteres vacio");
		}
		if (Validaciones.isTextoVacio(direccion)) {
			throw new ProduccionBLException(
					"La dirección del autor no puede ser nulo, ni una cadena de caracteres vacio");
		}
		if (Validaciones.isTextoVacio(apellidos)) {
			throw new ProduccionBLException(
					"El (los) apellido(s) del autor no puede ser nulo, ni una cadena de caracteres vacio");
		}
		if (Validaciones.isTextoVacio(nombres)) {
			throw new ProduccionBLException(
					"El nombre del autor no puede ser nula, ni una cadena de caracteres vacio");
		}
		if (Validaciones.isTextoVacio(id)) {
			throw new ProduccionBLException(
					"La identificación del autor no puede ser nula, ni una cadena de caracteres vacio");
		}

		try {
			TipoIdentificacion tipoidentificacion = tipoIdentificacionDAO
					.obtener(tipoIdentificacionIdn);
			System.out.println("TipoID: " + tipoIdentificacionIdn);
			TbAdmPersona persona = new TbAdmPersona();
			// persona.setUsuarioActualizacion("1");
			// personaDAO.insertar(persona);
			TbAdmPaises pais = paisDAO.obtener(nacionalidad);
			TbPrdAutor autor = new TbPrdAutor(pais, persona);

			autorDAO.insertarAutor(autor);
		} catch (ProduccionDAOException e) {
			throw new ProduccionBLException(e.getMessage());
		}
	}

	@Override
	public TbPrdAutor obtenerAutor(long id) throws ProduccionBLException {
		logger.info("--Obteniendo un autor--");
		try {
			return autorDAO.obtenerAutor(id);
		} catch (ProduccionDAOException e) {
			throw new ProduccionBLException(e.getMessage());
		}

	}

}
