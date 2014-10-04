package com.proint1.udea.produccion.dao;

import java.util.List;

import com.proint1.udea.administracion.entidades.terceros.TbAdmPersona;
import com.proint1.udea.produccion.util.ProduccionDAOException;

public interface PersonaDAO {

	public List<TbAdmPersona> listar();
	
	public void insertar(TbAdmPersona persona) throws ProduccionDAOException;
	
}
