package com.proint1.udea.produccion.ngc.impl;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.proint1.udea.administracion.entidades.terceros.TbAdmPaises;
import com.proint1.udea.administracion.entidades.terceros.TbAdmPersona;
import com.proint1.udea.administracion.entidades.terceros.TbAdmTipoIdentificacion;
import com.proint1.udea.produccion.dao.AutorDAO;
import com.proint1.udea.produccion.dao.PaisDAO;
import com.proint1.udea.produccion.dao.PersonaDAO;
import com.proint1.udea.produccion.dao.TipoIdentificacionDAO;
import com.proint1.udea.produccion.entidades.TbPrdAutor;
import com.proint1.udea.produccion.ngc.AutorService;
import com.proint1.udea.produccion.util.ArchivoInternacionalizacion;
import com.proint1.udea.produccion.util.ControlMensajes;
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
	public boolean insertar(long tipoIdentificacionIdn, String id,
			String apellidos, String nombre, String direccion, String email,
			String telefono, long nacionalidad, String foto)
			throws ProduccionBLException {
		
		//TODO Leer mensajes desde el archivo properties
		
		/**
		 * iniciamos validaciones antes de grabar el usuario
		 */
		if (Validaciones.isTextoVacio(nombre)) {
			throw new ProduccionBLException(
					"El nombre del autor no puede ser nulo, ni una cadena de caracteres vacio");
		}
		if (Validaciones.isTextoVacio(apellidos)) {
			throw new ProduccionBLException(
					"El (los) apellido(s) del autor no puede ser nulo, ni una cadena de caracteres vacio");
		}
		if (tipoIdentificacionIdn == 0) {
			throw new ProduccionBLException(
					"Debe serleccionar un tipo de identificación");
		}
		if (Validaciones.isTextoVacio(id)) {
			throw new ProduccionBLException(
					"El número de indentificación del autor no puede ser nulo, ni una cadena de caracteres vacio");
		}
		
		if (Validaciones.isTextoVacio(telefono)) {
			throw new ProduccionBLException(
					"El telefono del autor no puede ser nulo, ni una cadena de caracteres vacio");
		}

		try {
			//Busco la identificacion de la persona para ver si ya existe 
			TbAdmPersona persona = personaDAO.buscarPersona(id); 
			if (persona != null){
				ControlMensajes.mensajeInformation("No se puede crear el autor, pues ya existe otra persona con la misma identificación");
				return false; 
			}
			
			//Busco el tipo de identficación 
			TbAdmTipoIdentificacion tipoIdentificacion = tipoIdentificacionDAO.obtener(tipoIdentificacionIdn);
			
			//Creando la persona
			TbAdmPersona nuevaPersona = new TbAdmPersona(tipoIdentificacion, nombre, apellidos, id, direccion, telefono, email, "admin", new Date());
			personaDAO.insertar(nuevaPersona);
			
			//Busco el pais
			TbAdmPaises pais = paisDAO.obtener(nacionalidad);
			
			//Creo el autor
			TbPrdAutor autor = new TbPrdAutor(pais, nuevaPersona, null, "admin",new Date());
			autorDAO.insertarAutor(autor);
			
			return true;
			
		} catch (ProduccionDAOException e) {
			return false;
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
	
	@Override
	public TbPrdAutor bucarPersona(long id) throws ProduccionBLException {
		logger.info("--Obteniendo un autor--");
		try {
			return autorDAO.buscarPersona(id);
		} catch (ProduccionDAOException e) {
			throw new ProduccionBLException(e.getMessage());
		}

	}
}