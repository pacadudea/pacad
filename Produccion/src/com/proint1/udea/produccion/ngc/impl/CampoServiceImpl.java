package com.proint1.udea.produccion.ngc.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.proint1.udea.produccion.dao.CamposDAO;
import com.proint1.udea.produccion.entidades.TbPrdCampos;
import com.proint1.udea.produccion.ngc.CampoService;
import com.proint1.udea.produccion.util.ProduccionBLException;
import com.proint1.udea.produccion.util.ProduccionDAOException;
import com.proint1.udea.produccion.util.Validaciones;

public class CampoServiceImpl implements CampoService {
	
	private static final Logger logger = Logger.getLogger(CampoServiceImpl.class);
	
	CamposDAO campoDAO;

	@Override
	public List<TbPrdCampos> listar()throws ProduccionBLException  {
		List<TbPrdCampos> lista = null;
		try {
			lista=campoDAO.listar();
		}catch (ProduccionDAOException e){
			throw new ProduccionBLException(e.getMessage());
		}
		return lista;
	}

	
	//TODO  aun no esta claro la insercion con consecutivos.
	public void insertar(String vrDescripcion, long vrTipocampo,
			String nbTamañocampo, Boolean nbDecimales, String vrAdtusuario)
			throws ProduccionBLException {
		TbPrdCampos campo = null;
		/**
		 * Validamos la informacion de cada campo.
		 */
		try {
			if (Validaciones.isTextoVacio(vrDescripcion)) {
				throw new ProduccionBLException(
						"La descripcion del campo no puede ser nulo, ni una cadena de caracteres vacio");
			}
			if (vrTipocampo == 0) {
				throw new ProduccionBLException(
						"Debe seleccionar un tipo del campo.");
			}
			if (Validaciones.validarSoloNumeros(nbTamañocampo)) {
				throw new ProduccionBLException(
						"El tamaño del campo no puede ser nulo, ni una cadena de caracteres vacio");
			}
			if (Validaciones.isTextoVacio(vrAdtusuario)) {
				throw new ProduccionBLException(
						"La descripcion del campo no puede ser nulo, ni una cadena de caracteres vacio");
			}
			
			//campo.setNbIdn(nbIdn);
			campo.setVrTipocampo((char) vrTipocampo);
			campo.setVrDescripcion(vrDescripcion);
			campo.setNbTamañocampo(Short.valueOf(nbTamañocampo));
			campo.setVrAdtusuario(vrAdtusuario);
			campo.setNbDecimales(nbDecimales);
			campo.setBlEstado('1');
			campo.setDtAdtfecha(new Date());
			
			
			campoDAO.insertar(campo);
		} catch (ProduccionDAOException e) {
			throw new ProduccionBLException(e.getMessage());
		}
		
	}

	@Override
	public TbPrdCampos obtener(long id)throws ProduccionBLException{
		TbPrdCampos campo= null;
		try {
			campo = campoDAO.obtener(id);
		} catch (ProduccionDAOException e) {
			throw new ProduccionBLException(e.getMessage());
		}
		return campo;
	}

	public CamposDAO getCampoDAO() {
		return campoDAO;
	}

	public void setCampoDAO(CamposDAO campoDAO) {
		this.campoDAO = campoDAO;
	}


	
}
