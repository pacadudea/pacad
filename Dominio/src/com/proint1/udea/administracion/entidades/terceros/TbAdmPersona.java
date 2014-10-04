package com.proint1.udea.administracion.entidades.terceros;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidada que se encarga de realizar el mapeo de la entidad Persona
 * @author Edison
 * @version 2.0
 */
public class TbAdmPersona implements java.io.Serializable {

	private static final long serialVersionUID = 8119129525967693145L;
	
	private long nbIdn;
	private String vrNombres;
	private String vrApellidos;
	private long nbTipoidentificacionIdn;
	private String vrIdentificacion;
	private String vrDireccion;
	private String vrTelefono;
	private String vrEmail;
	private String vrAdtusuario;
	private Date dtAdtfecha;
	private Set tbPrdGrupoinvestigacionsForNbDirector = new HashSet(0);
	private Set tbPrdGrupoinvestigacionsForNbAuxiliar = new HashSet(0);
	private Set tbPrdMiembrosxgrupos = new HashSet(0);
	private Set tbPrdAutors = new HashSet(0);

	public TbAdmPersona() {
	}

	public TbAdmPersona(long nbIdn, String vrNombres, String vrApellidos,
			long nbTipoidentificacionIdn, String vrIdentificacion,
			String vrAdtusuario, Date dtAdtfecha) {
		this.nbIdn = nbIdn;
		this.vrNombres = vrNombres;
		this.vrApellidos = vrApellidos;
		this.nbTipoidentificacionIdn = nbTipoidentificacionIdn;
		this.vrIdentificacion = vrIdentificacion;
		this.vrAdtusuario = vrAdtusuario;
		this.dtAdtfecha = dtAdtfecha;
	}

	public TbAdmPersona(long nbIdn, String vrNombres, String vrApellidos,
			long nbTipoidentificacionIdn, String vrIdentificacion,
			String vrDireccion, String vrTelefono, String vrEmail,
			String vrAdtusuario, Date dtAdtfecha,
			Set tbPrdGrupoinvestigacionsForNbDirector,
			Set tbPrdGrupoinvestigacionsForNbAuxiliar,
			Set tbPrdMiembrosxgrupos, Set tbPrdAutors) {
		this.nbIdn = nbIdn;
		this.vrNombres = vrNombres;
		this.vrApellidos = vrApellidos;
		this.nbTipoidentificacionIdn = nbTipoidentificacionIdn;
		this.vrIdentificacion = vrIdentificacion;
		this.vrDireccion = vrDireccion;
		this.vrTelefono = vrTelefono;
		this.vrEmail = vrEmail;
		this.vrAdtusuario = vrAdtusuario;
		this.dtAdtfecha = dtAdtfecha;
		this.tbPrdGrupoinvestigacionsForNbDirector = tbPrdGrupoinvestigacionsForNbDirector;
		this.tbPrdGrupoinvestigacionsForNbAuxiliar = tbPrdGrupoinvestigacionsForNbAuxiliar;
		this.tbPrdMiembrosxgrupos = tbPrdMiembrosxgrupos;
		this.tbPrdAutors = tbPrdAutors;
	}

	public long getNbIdn() {
		return this.nbIdn;
	}

	public void setNbIdn(long nbIdn) {
		this.nbIdn = nbIdn;
	}

	public String getVrNombres() {
		return this.vrNombres;
	}

	public void setVrNombres(String vrNombres) {
		this.vrNombres = vrNombres;
	}

	public String getVrApellidos() {
		return this.vrApellidos;
	}

	public void setVrApellidos(String vrApellidos) {
		this.vrApellidos = vrApellidos;
	}

	public long getNbTipoidentificacionIdn() {
		return this.nbTipoidentificacionIdn;
	}

	public void setNbTipoidentificacionIdn(long nbTipoidentificacionIdn) {
		this.nbTipoidentificacionIdn = nbTipoidentificacionIdn;
	}

	public String getVrIdentificacion() {
		return this.vrIdentificacion;
	}

	public void setVrIdentificacion(String vrIdentificacion) {
		this.vrIdentificacion = vrIdentificacion;
	}

	public String getVrDireccion() {
		return this.vrDireccion;
	}

	public void setVrDireccion(String vrDireccion) {
		this.vrDireccion = vrDireccion;
	}

	public String getVrTelefono() {
		return this.vrTelefono;
	}

	public void setVrTelefono(String vrTelefono) {
		this.vrTelefono = vrTelefono;
	}

	public String getVrEmail() {
		return this.vrEmail;
	}

	public void setVrEmail(String vrEmail) {
		this.vrEmail = vrEmail;
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

	public Set getTbPrdGrupoinvestigacionsForNbDirector() {
		return this.tbPrdGrupoinvestigacionsForNbDirector;
	}

	public void setTbPrdGrupoinvestigacionsForNbDirector(
			Set tbPrdGrupoinvestigacionsForNbDirector) {
		this.tbPrdGrupoinvestigacionsForNbDirector = tbPrdGrupoinvestigacionsForNbDirector;
	}

	public Set getTbPrdGrupoinvestigacionsForNbAuxiliar() {
		return this.tbPrdGrupoinvestigacionsForNbAuxiliar;
	}

	public void setTbPrdGrupoinvestigacionsForNbAuxiliar(
			Set tbPrdGrupoinvestigacionsForNbAuxiliar) {
		this.tbPrdGrupoinvestigacionsForNbAuxiliar = tbPrdGrupoinvestigacionsForNbAuxiliar;
	}

	public Set getTbPrdMiembrosxgrupos() {
		return this.tbPrdMiembrosxgrupos;
	}

	public void setTbPrdMiembrosxgrupos(Set tbPrdMiembrosxgrupos) {
		this.tbPrdMiembrosxgrupos = tbPrdMiembrosxgrupos;
	}

	public Set getTbPrdAutors() {
		return this.tbPrdAutors;
	}

	public void setTbPrdAutors(Set tbPrdAutors) {
		this.tbPrdAutors = tbPrdAutors;
	}
}
