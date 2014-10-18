package com.proint1.udea.produccion.dto;

/**
 * DTO para el transporte de información respecto a los miembros del grupo de investigación
 * @author Edison
 *
 */

public class MiembrosGrupoInvestigacion implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Atributos resumidos sobre los miembros del grupo de investigacion
	 */
	private Integer idGrupo; 
	private String nombreGrugo;
	private Integer idPersona; 
	private	String nombreMiembro;
	private String apellidoMiembro; 
	
	public MiembrosGrupoInvestigacion() {
	}
	
	/**
	 * Constructor para generar instancia basica de la clase
	 * @param idGrupo identificacion del grupo de investigacion
	 * @param nombreGrugo nombre del grupo de investigacion
	 * @param idMiembro identificacion de miembro del grupo 
	 * @param nombreMiembro nombre de miembro del grupo 
	 * @param apellidoMiembro apellido de miembro del grupo
	 */
	public MiembrosGrupoInvestigacion(Integer idGrupo,String nombreGrugo, Integer idPersona,String nombreMiembro,String apellidoMiembro) {
		this.idGrupo = idGrupo;
		this.nombreGrugo = nombreGrugo;
		this.idPersona = idPersona;
		this.nombreMiembro = nombreMiembro;
		this.apellidoMiembro = apellidoMiembro;
	}

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getNombreGrugo() {
		return nombreGrugo;
	}

	public void setNombreGrugo(String nombreGrugo) {
		this.nombreGrugo = nombreGrugo;
	}

	public Integer getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Integer idPersona) {
		this.idPersona = idPersona;
	}

	public String getNombreMiembro() {
		return nombreMiembro;
	}

	public void setNombreMiembro(String nombreMiembro) {
		this.nombreMiembro = nombreMiembro;
	}

	public String getApellidoMiembro() {
		return apellidoMiembro;
	}

	public void setApellidoMiembro(String apellidoMiembro) {
		this.apellidoMiembro = apellidoMiembro;
	}
}
