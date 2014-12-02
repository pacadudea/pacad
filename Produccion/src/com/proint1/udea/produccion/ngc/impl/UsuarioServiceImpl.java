package com.proint1.udea.produccion.ngc.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.proint1.udea.administracion.entidades.seguridad.Usuario;
import com.proint1.udea.administracion.entidades.terceros.TbAdmPersona;
import com.proint1.udea.produccion.dao.UsuarioDAO;
import com.proint1.udea.produccion.ngc.UsuarioService;
import com.proint1.udea.produccion.util.ProduccionBLException;
import com.proint1.udea.produccion.util.ProduccionDAOException;

/**
 * Implementacion de los servicios de negocio para Usuario
 * 
 * @author Edison - Juan
 *
 */
public class UsuarioServiceImpl implements UsuarioService {

	private static Logger logger = Logger.getLogger(UsuarioServiceImpl.class);

	UsuarioDAO usuarioDAO;

	@Override
	public List<Usuario> listar() throws ProduccionBLException {
		try {
			return usuarioDAO.listar();
		} catch (ProduccionDAOException e) {
			throw new ProduccionBLException(e.getMessage());
		}
	}

	@Override
	public boolean insertarUsuario(long idPersona,
			String usuario, long usuarioActivo)
			throws ProduccionBLException {

		try {
			// Persona relacionada con el usuario
			TbAdmPersona p = new TbAdmPersona();
			p.setNbIdn(idPersona);

			// Creo el usuario
			Usuario user = new Usuario();
			user.setTbAdmPersona(p);
			user.setVrUsuLogin(usuario);
			user.setNbUsuActivo(usuarioActivo);
			
			usuarioDAO.insertarUsuario(user);
			return true;
		} catch (ProduccionDAOException e) {
			return false;
		}
	}

	@Override
	public boolean actualizarUsuario(long id, long idPersona,
			String usuario, long usuarioActivo)
			throws ProduccionBLException {
		
		try {
			// Creo el usuario
			Usuario user = new Usuario();
			user.setNbUsuIdn(id);
			user.setVrUsuLogin(usuario);
			user.setNbUsuActivo(usuarioActivo);

			usuarioDAO.editarUsuario(user);
			return true;

		} catch (ProduccionDAOException e) {
			return false;
		}
	}

	@Override
	public boolean eliminarUsuario(long id) throws ProduccionBLException {
		try {
			Usuario user = new Usuario();
			user.setNbUsuIdn(id);
			
			usuarioDAO.eliminarUsuario(user);
			return true;
		} catch (ProduccionDAOException e) {
			return false;
		}
	}

	@Override
	public Usuario encontrarUsuario(long idUsuario) throws ProduccionBLException{
		try {
			Usuario user = null;
			user = usuarioDAO.obtenerUsuario(idUsuario);
			return user;
		} catch (ProduccionDAOException e) {
			throw new ProduccionBLException(e.getMessage());
		}
	}

	@Override
	public Usuario loguearUsuario(String usuario, String pass)
			throws ProduccionBLException {
		try {
			Usuario user = null;
			user = usuarioDAO.loguearUsuario(usuario, pass);
			return user;
		} catch (ProduccionDAOException e) {
			throw new ProduccionBLException(e.getMessage());
		}
	}

	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	
}