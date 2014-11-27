package com.proint1.udea.produccion.dto;

import com.proint1.udea.produccion.entidades.TbPrdGrupoinvestigacion;
import com.proint1.udea.produccion.entidades.TbPrdTipoproduccion;

/**
 * DTO para el transporte de información respecto a los miembros del grupo de investigación
 * @author Edison
 *
 */

public class TipoProduccionxGrupo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private TbPrdGrupoinvestigacion grupo; 
	private TbPrdTipoproduccion tipo; 
	private int cantidad;
	 
	
	public TipoProduccionxGrupo() {
	}


	public TbPrdGrupoinvestigacion getGrupo() {
		return grupo;
	}


	public void setGrupo(TbPrdGrupoinvestigacion grupo) {
		this.grupo = grupo;
	}


	public TbPrdTipoproduccion getTipo() {
		return tipo;
	}


	public void setTipo(TbPrdTipoproduccion tipo) {
		this.tipo = tipo;
	}


	public int getCantidad() {
		return cantidad;
	}


	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	
}
