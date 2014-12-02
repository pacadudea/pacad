package com.proint1.udea.produccion.ctl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.ext.Selectable;

import com.proint1.udea.produccion.dto.TipoProduccionxGrupo;
import com.proint1.udea.produccion.entidades.TbPrdAutor;
import com.proint1.udea.produccion.entidades.TbPrdAutoresxproduccion;
import com.proint1.udea.produccion.entidades.TbPrdGrupoinvestigacion;
import com.proint1.udea.produccion.entidades.TbPrdTipoproduccion;
import com.proint1.udea.produccion.ngc.GrupoInvestigacionService;
import com.proint1.udea.produccion.util.ControlMensajes;
import com.proint1.udea.produccion.util.ProduccionBLException;
import com.proint1.udea.produccion.util.ProduccionDAOException;
import com.proint1.udea.produccion.util.ProduccionIWException;
import com.proint1.udea.produccion.util.VistasZk;

public class DetalleGruposInvestigacionCtl extends GenericForwardComposer implements ListitemRenderer {
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(DetalleGruposInvestigacionCtl.class);
	
	//Serviccios a usar
	GrupoInvestigacionService grupoInvestigacionService;
	
	//Lista con todos los autores que existen en el aplicativo
	private List<TbPrdGrupoinvestigacion> listaGrupos;
	private TbPrdGrupoinvestigacion grupoSeleccionado;
	
	//Componentes del formulario de datos
	Listbox listBoxGrupos;
	Listbox listBoxResumenTipo;
	
	Textbox txtFiltrarGrupo;
	
	Label lbNombreGrupo;
	Label lbAbreviatura;
	Label lbEstado;
	Label lbDirector;
	Label lbAuxiliar;
	Label sinResumen;
	
	Window winDetalleGrupoInvestigacion;
	
	public void onCreate() throws ProduccionDAOException, ProduccionBLException, ProduccionIWException {
		this.cargarGrupos();
	}

	
	/**
	 * Carga todos los grupos de investigacion existentes en la aplicación
	 * @throws ProduccionIWException: En caso de que existan errores en la carga de los datos
	 */
	private void cargarGrupos() throws ProduccionIWException{
		try {
			this.listaGrupos = grupoInvestigacionService.listar();
			listBoxGrupos.setModel(new ListModelList<TbPrdGrupoinvestigacion>(this.listaGrupos));
			
		} catch (Exception e) {
			ControlMensajes.mensajeError(Labels.getLabel("pacad.mensajeError.noCargaDatos"));
		}
	}
	
	public void onChange$txtFiltrarGrupo() {
		List<TbPrdGrupoinvestigacion> listaFiltrada = new ArrayList<TbPrdGrupoinvestigacion>();
		String filtro = this.txtFiltrarGrupo.getValue().toLowerCase();
		for (TbPrdGrupoinvestigacion grupo : this.listaGrupos) {
			if (grupo.getVrNombre().toLowerCase().contains(filtro)){
				listaFiltrada.add(grupo);
			}
		}
		this.listBoxGrupos.setModel(new ListModelList<TbPrdGrupoinvestigacion>(listaFiltrada));
	}

	/**
	 * Detecta la seleccion de uno de un grupo y carga su informacion para ser modificada
	 * @throws ProduccionBLException
	 * @throws ProduccionIWException
	 */
	public void onSelect$listBoxGrupos() throws ProduccionIWException  {
		Set<TbPrdGrupoinvestigacion> selection = ((Selectable<TbPrdGrupoinvestigacion>)listBoxGrupos.getModel()).getSelection();
		this.grupoSeleccionado = selection.iterator().next();
		this.llenarDatos();
	}
	
	private void llenarDatos()  {
		TbPrdGrupoinvestigacion grupo = grupoSeleccionado;
		//Lleno los datos
		this.lbNombreGrupo.setValue(grupo.getVrNombre());
		this.lbAbreviatura.setValue(grupo.getVrAbreviatura());
		this.lbDirector.setValue(grupo.getTbAdmPersonaByNbDirector().getVrNombres());
		this.lbAuxiliar.setValue(grupo.getTbAdmPersonaByNbAuxiliar().getVrNombres());	
		this.lbEstado.setValue(grupo.getBlEstado() == 'A' ? Labels.getLabel("pacad.texto.activo"):Labels.getLabel("pacad.texto.inactivo"));
		
		//Genero la lista de tipo de producciones
		List<TipoProduccionxGrupo> listaFin = new ArrayList<TipoProduccionxGrupo>();
		
		Set autoresxProds = this.grupoSeleccionado.getTbPrdAutoresxproduccions();
		for (Iterator iterator = autoresxProds.iterator(); iterator.hasNext();) {
			TbPrdAutoresxproduccion auxP = (TbPrdAutoresxproduccion) iterator.next();
			String tipo = auxP.getTbPrdProduccion().getTbPrdTipoproduccion().getVrDescripcion();
			
			if (this.existeTipo(listaFin, tipo)){
				for (TipoProduccionxGrupo tipoP : listaFin) {
					if (tipoP.getTipo().getVrDescripcion().equals(tipo)){
						tipoP.setCantidad(tipoP.getCantidad() + 1);
						break;
					}	
				}
			}else{
				TipoProduccionxGrupo nuevTipo = new TipoProduccionxGrupo();
				nuevTipo.setCantidad(1);
				nuevTipo.setGrupo(grupoSeleccionado);
				nuevTipo.setTipo(auxP.getTbPrdProduccion().getTbPrdTipoproduccion());
				listaFin.add(nuevTipo);
			}
		}
		
		if (listaFin.size() > 0){		
			listBoxResumenTipo.setModel(new ListModelList<TipoProduccionxGrupo>(listaFin));
			this.listBoxResumenTipo.setItemRenderer(this);
			
			sinResumen.setVisible(false);
			listBoxResumenTipo.setVisible(true);
		}else{
			sinResumen.setVisible(true);
			listBoxResumenTipo.setVisible(false);
		}
	}
	
	@Override
	public void render(Listitem arg0, Object arg1, int arg2) throws Exception {
		String estiloLink= "color:blue; text-decoration:underline;font-weight:bold";
		
		//Obtengo el objeto pra cada uno de los rows
		TipoProduccionxGrupo t = (TipoProduccionxGrupo)arg1;
		
		//Creo las celdas correspondientes
		Listcell cellTipo = new Listcell();
		cellTipo.setLabel(t.getTipo().getVrDescripcion());
		cellTipo.setStyle(estiloLink);
		cellTipo.addEventListener(Events.ON_CLICK, new irDetalleTipo(t.getGrupo() , t.getTipo()));
		
		Listcell cellCantidad = new Listcell();
		cellCantidad.setLabel(t.getCantidad()+"");
			
		arg0.appendChild(cellTipo);
		arg0.appendChild(cellCantidad);
	}
	
	private boolean existeTipo(List<TipoProduccionxGrupo> lista, String tipo){
		for (TipoProduccionxGrupo tipoProduccionxGrupo : lista) {
			if (tipoProduccionxGrupo.getTipo().getVrDescripcion().equals(tipo)){
				return true;
			}
		}
		return false;
	}
		
	public void onClick$lbAuxiliar(){
		TbPrdAutor auxiliar; 
		auxiliar = (TbPrdAutor) this.grupoSeleccionado.getTbAdmPersonaByNbAuxiliar().getTbPrdAutors().iterator().next();
		this.irDetalleAutor(auxiliar);
	}
	public void onClick$lbDirector(){
		TbPrdAutor director; 
		director = (TbPrdAutor) this.grupoSeleccionado.getTbAdmPersonaByNbDirector().getTbPrdAutors().iterator().next();
		this.irDetalleAutor(director);
	}
	
	
	public void irDetalleAutor(TbPrdAutor autor){
		Map args = new HashMap<>();
		args.put("autor", autor);
		
		java.io.InputStream zulInput = this.getClass().getClassLoader().getResourceAsStream("com/proint1/udea/produccion/vista/detalleAutor.zul") ;
		java.io.Reader zulReader = new java.io.InputStreamReader(zulInput);
		
		Window window;
		try {
			Div divCenter = VistasZk.obtenerDivCenter(winDetalleGrupoInvestigacion);
			divCenter.getChildren().clear();
			window= (Window)Executions.createComponentsDirectly(zulReader,"zul",divCenter,args) ;	
			window.doEmbedded();
		} catch (IOException e) {
			System.err.println("ERROR ENVIANDO");
			e.printStackTrace();
		}
	}
	
	public GrupoInvestigacionService getGrupoInvestigacionService() {
		return grupoInvestigacionService;
	}

	public void setGrupoInvestigacionService(
			GrupoInvestigacionService grupoInvestigacionService) {
		this.grupoInvestigacionService = grupoInvestigacionService;
	}

	private class irDetalleTipo implements EventListener {
	
		private TbPrdGrupoinvestigacion grupo;
		private TbPrdTipoproduccion tipo;
		
		public irDetalleTipo(TbPrdGrupoinvestigacion agrupo, TbPrdTipoproduccion atipo){
			this.grupo = agrupo;
			this.tipo = atipo;
		}
	
		public void onEvent(Event event){	
			Map args = new HashMap<>();
			args.put("grupo", grupo);
			args.put("tipo", tipo);
	
			java.io.InputStream zulInput = this.getClass().getClassLoader().getResourceAsStream("com/proint1/udea/produccion/vista/detalleGrupoXTipoProd.zul") ;
			java.io.Reader zulReader = new java.io.InputStreamReader(zulInput);
	
			Window window;
			try {
				Div divCenter = VistasZk.obtenerDivCenter(winDetalleGrupoInvestigacion);
				divCenter.getChildren().clear();
				window= (Window)Executions.createComponentsDirectly(zulReader,"zul",divCenter,args) ;	
				window.doEmbedded();
			} catch (IOException e) {
				System.err.println("ERROR ENVIANDO");
				e.printStackTrace();
			}
		}
	}

}
