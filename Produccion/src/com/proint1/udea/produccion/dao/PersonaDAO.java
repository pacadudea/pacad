package com.proint1.udea.produccion.dao;

import java.util.List;

import com.proint1.udea.administracion.entidades.terceros.Persona;
import com.proint1.udea.produccion.util.ProduccionDAOException;

public interface PersonaDAO {

	public List<Persona> listar();
	
	public void insertar(Persona persona) throws ProduccionDAOException;
	
}
