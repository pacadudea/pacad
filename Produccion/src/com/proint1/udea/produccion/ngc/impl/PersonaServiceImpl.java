package com.proint1.udea.produccion.ngc.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.proint1.udea.administracion.entidades.terceros.TbAdmPersona;
import com.proint1.udea.produccion.dao.PersonaDAO;
import com.proint1.udea.produccion.ngc.PersonaService;
import com.proint1.udea.produccion.util.ProduccionBLException;
import com.proint1.udea.produccion.util.ProduccionDAOException;

public class PersonaServiceImpl implements PersonaService {

	private static Logger logger = Logger.getLogger(PersonaServiceImpl.class);

	PersonaDAO personaDAO;
	
	@Override
	public List<TbAdmPersona> listar() {
		logger.info("-- Iniciando consulta de personas");
		  return personaDAO.listar();		
	}

	public PersonaDAO getPersonaDAO() {
		return personaDAO;
	}

	public void setPersonaDAO(PersonaDAO personaDAO) {
		this.personaDAO = personaDAO;
	}

	@Override
	public TbAdmPersona buscarPersona(String identificacion) throws ProduccionBLException {
		try {
			return personaDAO.buscarPersona(identificacion);
		} catch (ProduccionDAOException e) {
			throw new ProduccionBLException(e.getMessage());
		}
	} 

	
}
