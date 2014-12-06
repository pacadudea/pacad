package com.proint1.udea.ctl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.zkoss.lang.Library;
import org.zkoss.web.Attributes;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Div;
import org.zkoss.zul.Menu;
import org.zkoss.zul.Window;

import com.proint1.udea.produccion.util.ProduccionException;
import com.proint1.udea.produccion.util.Seguridad;

public class MenuPrincipalHorCtl  extends GenericForwardComposer{
	
	Div divCenter;
	Menu menuConsultas;
	Menu menuAdmin;
	
	private static Logger logger=Logger.getLogger(MenuPrincipalHorCtl.class);
	
	public void onCreate(){
	}

	public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        
        System.out.println("CARGANDO PRINCIPAL");
        Seguridad.cargarPagina(self);
        
        boolean admin = Seguridad.accesoAdministrador();
        if (!admin){
        	this.menuAdmin.detach();
        }
        
       
        
        
        logger.info("cargando MenuPrincipalHorCtl");     
   }
	
	public void onClick$btnAdministracion() throws IOException{
		if(divCenter==null){
			divCenter = (Div)Sessions.getCurrent().getAttribute("divPrincipalCtl");
		}
		java.io.InputStream zulInput = this.getClass().getClassLoader().getResourceAsStream("com/proint1/udea/administracion/vista/consultarPersona.zul") ; 
		java.io.Reader zulReader = new java.io.InputStreamReader(zulInput);
		Window windowCenter= (Window)Executions.createComponentsDirectly(zulReader,"zul",divCenter,new HashMap()) ;	
		windowCenter.doEmbedded();
		windowCenter.setClass("window");
	}
	
	public void onClick$btnCurriculo(){
		if(divCenter==null){
			divCenter = (Div)Sessions.getCurrent().getAttribute("divPrincipalCtl");
		}
	}
	
	public void onClick$btnReporteActividades(){
		if(divCenter==null){
			divCenter = (Div)Sessions.getCurrent().getAttribute("divPrincipalCtl");
		}
	}
	
	public void onClick$gestionAutoresMenu() throws IOException{
		if(divCenter==null){
			divCenter = (Div)Sessions.getCurrent().getAttribute("divPrincipalCtl");
		}
		divCenter.getChildren().clear();
		java.io.InputStream zulInput = this.getClass().getClassLoader().getResourceAsStream("com/proint1/udea/produccion/vista/gestionAutores.zul") ; 
		java.io.Reader zulReader = new java.io.InputStreamReader(zulInput);
		Window windowCenter= (Window)Executions.createComponentsDirectly(zulReader,"zul",divCenter,new HashMap()) ;	
		windowCenter.doEmbedded();
		windowCenter.setClass("window");
	}
	
	public void onClick$gestionGrupoMenu() throws ProduccionException{
		if(divCenter==null){
			divCenter = (Div)Sessions.getCurrent().getAttribute("divPrincipalCtl");
		}
		divCenter.getChildren().clear();
		java.io.InputStream zulInput = this.getClass().getClassLoader().getResourceAsStream("com/proint1/udea/produccion/vista/gestionGruposInvestigacion.zul") ; 
		java.io.Reader zulReader = new java.io.InputStreamReader(zulInput);
		Window windowCenter;
		try {
			windowCenter = (Window)Executions.createComponentsDirectly(zulReader,"zul",divCenter,new HashMap());
			windowCenter.doEmbedded();
			windowCenter.setClass("window");
		} catch (IOException e) {
			throw new ProduccionException("ERROR :: No se encuentra la vista de gestion de mimbros de producción" + e.getMessage());
		}	
	}
	
	public void onClick$gestionProduccionMenu() throws IOException{
		if(divCenter==null){
			divCenter = (Div)Sessions.getCurrent().getAttribute("divPrincipalCtl");
		}
		divCenter.getChildren().clear();
		java.io.InputStream zulInput = this.getClass().getClassLoader().getResourceAsStream("com/proint1/udea/produccion/vista/registroProduccion.zul") ; 
		java.io.Reader zulReader = new java.io.InputStreamReader(zulInput);
		Window windowCenter= (Window)Executions.createComponentsDirectly(zulReader,"zul",divCenter,new HashMap()) ;	
		windowCenter.doEmbedded();
		windowCenter.setClass("window");
	}
	
	public void onClick$gestionTipoProduccionMenu() throws IOException{
		if(divCenter==null){
			divCenter = (Div)Sessions.getCurrent().getAttribute("divPrincipalCtl");
		}
		divCenter.getChildren().clear();
		java.io.InputStream zulInput = this.getClass().getClassLoader().getResourceAsStream("com/proint1/udea/produccion/vista/gestionTiposProduccion.zul") ; 
		java.io.Reader zulReader = new java.io.InputStreamReader(zulInput);
		Window windowCenter= (Window)Executions.createComponentsDirectly(zulReader,"zul",divCenter,new HashMap()) ;	
		windowCenter.doEmbedded();
		windowCenter.setClass("window");
	}
	
	public void onClick$reporteDinamicoProduccionMenu() throws IOException{
		if(divCenter==null){
			divCenter = (Div)Sessions.getCurrent().getAttribute("divPrincipalCtl");
		}
		divCenter.getChildren().clear();
		java.io.InputStream zulInput = this.getClass().getClassLoader().getResourceAsStream("com/proint1/udea/produccion/vista/reporteDinamicoProducciones.zul") ; 
		java.io.Reader zulReader = new java.io.InputStreamReader(zulInput);
		Window windowCenter= (Window)Executions.createComponentsDirectly(zulReader,"zul",divCenter,new HashMap()) ;	
		windowCenter.doEmbedded();
		windowCenter.setClass("window");
	}
	
	public void onClick$consultaInteractiva() throws IOException{
		if(divCenter==null){
			divCenter = (Div)Sessions.getCurrent().getAttribute("divPrincipalCtl");
		}
		divCenter.getChildren().clear();
		java.io.InputStream zulInput = this.getClass().getClassLoader().getResourceAsStream("com/proint1/udea/produccion/vista/consultaInteractiva.zul") ; 
		java.io.Reader zulReader = new java.io.InputStreamReader(zulInput);
		Window windowCenter= (Window)Executions.createComponentsDirectly(zulReader,"zul",divCenter,new HashMap()) ;	
		windowCenter.doEmbedded();
		windowCenter.setClass("window");
	}
	
	public void onClick$consultaGrupos() throws IOException{
		if(divCenter==null){
			divCenter = (Div)Sessions.getCurrent().getAttribute("divPrincipalCtl");
		}
		divCenter.getChildren().clear();
		java.io.InputStream zulInput = this.getClass().getClassLoader().getResourceAsStream("com/proint1/udea/produccion/vista/detalleGruposInvestigacion.zul") ; 
		java.io.Reader zulReader = new java.io.InputStreamReader(zulInput);
		Window windowCenter= (Window)Executions.createComponentsDirectly(zulReader,"zul",divCenter,new HashMap()) ;	
		windowCenter.doEmbedded();
		windowCenter.setClass("window");
	}
	
	public void onClick$consultaFiltrada() throws IOException{
		if(divCenter==null){
			divCenter = (Div)Sessions.getCurrent().getAttribute("divPrincipalCtl");
		}
		divCenter.getChildren().clear();
		java.io.InputStream zulInput = this.getClass().getClassLoader().getResourceAsStream("com/proint1/udea/produccion/vista/data_filter.zul") ; 
		java.io.Reader zulReader = new java.io.InputStreamReader(zulInput);
		Window windowCenter= (Window)Executions.createComponentsDirectly(zulReader,"zul",divCenter,new HashMap()) ;	
		windowCenter.doEmbedded();
		windowCenter.setClass("window");
	}
	
	public void onClick$produccionesFiltradas() throws IOException{
		if(divCenter==null){
			divCenter = (Div)Sessions.getCurrent().getAttribute("divPrincipalCtl");
		}
		divCenter.getChildren().clear();
		java.io.InputStream zulInput = this.getClass().getClassLoader().getResourceAsStream("com/proint1/udea/produccion/vista/produccionesFiltradas.zul") ; 
		java.io.Reader zulReader = new java.io.InputStreamReader(zulInput);
		Window windowCenter= (Window)Executions.createComponentsDirectly(zulReader,"zul",divCenter,new HashMap()) ;	
		windowCenter.doEmbedded();
		windowCenter.setClass("window");
	}
	
	public void onClick$gestionMiembros() throws ProduccionException {
		if(divCenter==null){
			divCenter = (Div)Sessions.getCurrent().getAttribute("divPrincipalCtl");
		}
		divCenter.getChildren().clear();
		java.io.InputStream zulInput = this.getClass().getClassLoader().getResourceAsStream("com/proint1/udea/produccion/vista/gestionMiembros.zul") ; 
		java.io.Reader zulReader = new java.io.InputStreamReader(zulInput);
		Window windowCenter;
		try {
			
			windowCenter = (Window)Executions.createComponentsDirectly(zulReader,"zul",divCenter,new HashMap());
			windowCenter.doEmbedded();
			windowCenter.setClass("window");
		} catch (IOException e) {
			throw new ProduccionException("ERROR :: No se encuentra la vista de gestion de miembros de producción" + e.getMessage());
		}	
	}
	
	public void onClick$reportes() throws IOException{
		if(divCenter==null){
			divCenter = (Div)Sessions.getCurrent().getAttribute("divPrincipalCtl");
		}
		divCenter.getChildren().clear();
		java.io.InputStream zulInput = this.getClass().getClassLoader().getResourceAsStream("com/proint1/udea/produccion/vista/reportes.zul") ; 
		java.io.Reader zulReader = new java.io.InputStreamReader(zulInput);
		Window windowCenter= (Window)Executions.createComponentsDirectly(zulReader,"zul",divCenter,new HashMap<Object, Object>()) ;	
		windowCenter.doEmbedded();
		windowCenter.setClass("window");
	}
	                    						
	public void onClick$mCerrarSesion() throws ProduccionException {
		System.out.println("#cerrando");
		Seguridad.cerrarSesion();	
	}
}
