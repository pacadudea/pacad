package com.proint1.udea.produccion.ctl;

public class ProduccionFiltro {

	private String TipoProd="", Id="", Descripcion="", Estado="";
	
	
    public String getTipoProd() {
		return TipoProd;
	}

	public String getId() {
		return Id;
	}

	public String getDescripcion() {
		return Descripcion;
	}
	
	public String getEstado() {
		return Estado;
	}
	
	public void setTipoProd(String tipoProd) {
		this.TipoProd = tipoProd==null?"":tipoProd.trim();
	}

	public void setId(String id) {
		this.Id = id==null?"":id.trim();
	}

	public void setDescripcion(String descripcion) {
		this.Descripcion = descripcion==null?"":descripcion.trim();
	}

	public void setEstado(String estado) {
		this.Estado = estado==null?"":estado.trim();
	}

 
    
}
