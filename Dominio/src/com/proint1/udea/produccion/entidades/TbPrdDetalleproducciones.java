package com.proint1.udea.produccion.entidades;

import java.util.Date;

/**
 * 
 * @author Edison
 *
 */
public class TbPrdDetalleproducciones implements java.io.Serializable {

	private long nbIdn;
	private TbPrdProduccion tbPrdProduccion;
	private TbPrdTipoproduccionesxcampo tbPrdTipoproduccionesxcampo;
	private String vrValor;
	private char blEstado;
	private String vrAdtusuario;
	private Date dtAdtfecha;

	public TbPrdDetalleproducciones() {
	}

	public TbPrdDetalleproducciones(long nbIdn,
			TbPrdProduccion tbPrdProduccion,
			TbPrdTipoproduccionesxcampo tbPrdTipoproduccionesxcampo,
			char blEstado, String vrAdtusuario, Date dtAdtfecha) {
		this.nbIdn = nbIdn;
		this.tbPrdProduccion = tbPrdProduccion;
		this.tbPrdTipoproduccionesxcampo = tbPrdTipoproduccionesxcampo;
		this.blEstado = blEstado;
		this.vrAdtusuario = vrAdtusuario;
		this.dtAdtfecha = dtAdtfecha;
	}

	public TbPrdDetalleproducciones(long nbIdn,
			TbPrdProduccion tbPrdProduccion,
			TbPrdTipoproduccionesxcampo tbPrdTipoproduccionesxcampo,
			String vrValor, char blEstado, String vrAdtusuario, Date dtAdtfecha) {
		this.nbIdn = nbIdn;
		this.tbPrdProduccion = tbPrdProduccion;
		this.tbPrdTipoproduccionesxcampo = tbPrdTipoproduccionesxcampo;
		this.vrValor = vrValor;
		this.blEstado = blEstado;
		this.vrAdtusuario = vrAdtusuario;
		this.dtAdtfecha = dtAdtfecha;
	}

	public long getNbIdn() {
		return this.nbIdn;
	}

	public void setNbIdn(long nbIdn) {
		this.nbIdn = nbIdn;
	}

	public TbPrdProduccion getTbPrdProduccion() {
		return this.tbPrdProduccion;
	}

	public void setTbPrdProduccion(TbPrdProduccion tbPrdProduccion) {
		this.tbPrdProduccion = tbPrdProduccion;
	}

	public TbPrdTipoproduccionesxcampo getTbPrdTipoproduccionesxcampo() {
		return this.tbPrdTipoproduccionesxcampo;
	}

	public void setTbPrdTipoproduccionesxcampo(
			TbPrdTipoproduccionesxcampo tbPrdTipoproduccionesxcampo) {
		this.tbPrdTipoproduccionesxcampo = tbPrdTipoproduccionesxcampo;
	}

	public String getVrValor() {
		return this.vrValor;
	}

	public void setVrValor(String vrValor) {
		this.vrValor = vrValor;
	}

	public char getBlEstado() {
		return this.blEstado;
	}

	public void setBlEstado(char blEstado) {
		this.blEstado = blEstado;
	}

	public String getVrAdtusuario() {
		return this.vrAdtusuario;
	}

	public void setVrAdtusuario(String vrAdtusuario) {
		this.vrAdtusuario = vrAdtusuario;
	}

	public Date getDtAdtfecha() {
		return this.dtAdtfecha;
	}

	public void setDtAdtfecha(Date dtAdtfecha) {
		this.dtAdtfecha = dtAdtfecha;
	}

}
