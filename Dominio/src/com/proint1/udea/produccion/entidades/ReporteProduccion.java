package com.proint1.udea.produccion.entidades;

public class ReporteProduccion {
	
	String nombreProduccion;
	String tipoProduccion;
	String autores;
	String fechaPublicacion;
	String urlProduccion;
	String doiProduccion;
	String detalles;
	String grupoInvestigacion;
	
	
	public ReporteProduccion(){
		
	}
	
	public ReporteProduccion(String nombreProd, String tipoProd, String autores){
		this.nombreProduccion = nombreProd;
		this.tipoProduccion = tipoProd;
		this.autores = autores;
	}
	
	public String getGrupoInvestigacion() {
		return grupoInvestigacion;
	}

	public void setGrupoInvestigacion(String grupoInvestigacion) {
		this.grupoInvestigacion = grupoInvestigacion;
	}

	public String getDetalles() {
		return detalles;
	}

	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}
	
	public String getNombreProduccion() {
		return nombreProduccion;
	}

	public void setNombreProduccion(String nombreProduccion) {
		this.nombreProduccion = nombreProduccion;
	}

	public String getTipoProduccion() {
		return tipoProduccion;
	}

	public void setTipoProduccion(String tipoProduccion) {
		this.tipoProduccion = tipoProduccion;
	}

	public String getAutores() {
		return autores;
	}

	public void setAutores(String autores) {
		this.autores = autores;
	}
	
	public String getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(String fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}
	
	public String getUrlProduccion() {
		return urlProduccion;
	}

	public void setUrlProduccion(String urlProduccion) {
		this.urlProduccion = urlProduccion;
	}

	public String getDoiProduccion() {
		return doiProduccion;
	}

	public void setDoiProduccion(String doiProduccion) {
		this.doiProduccion = doiProduccion;
	}
}
