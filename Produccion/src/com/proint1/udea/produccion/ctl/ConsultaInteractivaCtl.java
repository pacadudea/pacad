package com.proint1.udea.produccion.ctl;

import java.awt.Button;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.proint1.udea.administracion.entidades.terceros.TbAdmPersona;
import com.proint1.udea.produccion.entidades.TbPrdProduccion;
import com.proint1.udea.produccion.ngc.AutorService;
import com.proint1.udea.produccion.ngc.ProduccionService;
import com.proint1.udea.produccion.util.ControlMensajes;
import com.proint1.udea.produccion.util.ProduccionBLException;
import com.proint1.udea.produccion.util.ProduccionIWException;
import com.proint1.udea.produccion.util.VistasZk;

public class ConsultaInteractivaCtl extends GenericForwardComposer implements ListitemRenderer{
	
	private static final long serialVersionUID = 1L;
	List<TbPrdProduccion> result;
	

	private static Logger logger=Logger.getLogger(ConsultaInteractivaCtl.class);
	
	private TbPrdProduccion produccionSeleccionada;
	private TbAdmPersona autorSeleccionado;
	
	
	/**
	 * beans del negocio usados
	 */
	ProduccionService produccionService;
	AutorService autorService;
	
	//Temporal
	Button bt2;
	
	Window reporte;
	
	//Campos para filtrar
	Textbox filNombre;
	Textbox filTipoProduccion;
	Textbox filEstado;
	
	Listbox listaProducciones;
	

	/*public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}*/
	
	/**
	 * Carga inicial de la ventana
	 * @throws ProduccionBLException
	 * @throws ProduccionIWException 
	 */
	public void onCreate(){
		System.err.println("CARGANDO VENTANA");
		this.cargarProducciones();
	}
	
	public void onClick$bt2() {
		Map a = new HashMap<>();
		a.put("juan", "castro");
		a.put("marcela", "giraldo");
		/*reporte.getPosition()
		reporte.getStyle()
		reporte.detach();*/
		/*
		 * if(divCenter==null){
			divCenter = (Div)Sessions.getCurrent().getAttribute("divPrincipalCtl");
		}
		divCenter.getChildren().clear();
		java.io.InputStream zulInput = this.getClass().getClassLoader().getResourceAsStream("com/proint1/udea/produccion/vista/consultaInteractiva.zul") ; 
		java.io.Reader zulReader = new java.io.InputStreamReader(zulInput);
		Window windowCenter= (Window)Executions.createComponentsDirectly(zulReader,"zul",divCenter,new HashMap()) ;	
		windowCenter.doEmbedded();
		windowCenter.setClass("window");
		 * 
		 * */
		java.io.InputStream zulInput = this.getClass().getClassLoader().getResourceAsStream("com/proint1/udea/produccion/vista/employee_dialog.zul") ;
		java.io.Reader zulReader = new java.io.InputStreamReader(zulInput);
		
		Window window;
		
		try {
			
			/*Div divCenter = (Div)Sessions.getCurrent().getAttribute("divPrincipalCtl");
			if (divCenter == null  ){
				System.err.println("no se encontro el div");
			}else{
				System.err.println("SE encontro el div");
				
			}*/
			Div divCenter = VistasZk.obtenerDivCenter(reporte);
			if (divCenter == null  ){
				System.err.println("no se encontro el div");
			}else{
				System.err.println("SE encontro el div");
				
			}
			
			
			divCenter.getChildren().clear();
			window= (Window)Executions.createComponentsDirectly(zulReader,"zul",divCenter,a) ;	
			
			window.doEmbedded();
			//window.setStyle(reporte.getStyle());
			//window.setClass("window");
			 // window.doModal();
		} catch (IOException e) {
			System.err.println("ERROR ENVIANDO");
			e.printStackTrace();
		}
      
	}
	
	
	public void onChange$filNombre() {
		List<TbPrdProduccion> listaFiltrada = new ArrayList<TbPrdProduccion>();
		String filtro = this.filNombre.getValue().toLowerCase();
		for (TbPrdProduccion tbPrdProduccion : result) {
			if (tbPrdProduccion.getVrNombreproduccion().toLowerCase().contains(filtro)){
				listaFiltrada.add(tbPrdProduccion);
			}
			
		}
		listaProducciones.setModel(new ListModelList<TbPrdProduccion>(listaFiltrada));
	}
	
	public void onChange$filEstado() {
		List<TbPrdProduccion> listaFiltrada = new ArrayList<TbPrdProduccion>();
		String filtro = this.filEstado.getValue().toLowerCase();
		for (TbPrdProduccion tbPrdProduccion : result) {
			String estado = tbPrdProduccion.getBlEstado() +	"";
			if (estado.toLowerCase().contains(filtro)){
				listaFiltrada.add(tbPrdProduccion);
			}
		}
		listaProducciones.setModel(new ListModelList<TbPrdProduccion>(listaFiltrada));
	}
	
	public void onChange$filTipoProduccion() {
		List<TbPrdProduccion> listaFiltrada = new ArrayList<TbPrdProduccion>();
		String filtro = this.filTipoProduccion.getValue().toLowerCase();
		for (TbPrdProduccion tbPrdProduccion : result) {
			if (tbPrdProduccion.getTbPrdTipoproduccion().getVrDescripcion().toLowerCase().contains(filtro)){
				listaFiltrada.add(tbPrdProduccion);
			}
		}
		listaProducciones.setModel(new ListModelList<TbPrdProduccion>(listaFiltrada));
	}
	
	
	
	/**
	 * Detecta la seleccion de uno de los grupos de investigación y carga las listas de miembros y posibles miembros
	 * @throws ProduccionBLException
	 * @throws ProduccionIWException
	 */
	/*public void onSelect$listaProducciones() throws ProduccionIWException  {
		//Detecto la seleccion y la convierto a una clases
		Set<TbPrdProduccion> selection = ((Selectable<TbPrdProduccion>)listaProducciones.getModel()).getSelection();
		TbPrdProduccion produccionSelected = selection.iterator().next();
		this.produccionSeleccionada = produccionSelected;
		this.prodd.setValue(produccionSelected.getVrNombreproduccion());
		
		Set<TbPrdAutoresxproduccion> set = this.produccionSeleccionada.getTbPrdAutoresxproduccions();
		
		List<TbAdmPersona> listaAutores = new ArrayList<TbAdmPersona>(); 
		for (TbPrdAutoresxproduccion s : set) {
			TbAdmPersona autor = s.getTbPrdAutor().getPersona();
			listaAutores.add(autor);
		}
		this.autoresxProduccion = listaAutores;
	}*/
	
	private void cargarProducciones(){
		try {
			result =  produccionService.listar();
			for (Iterator iterator = result.iterator(); iterator.hasNext();) {
				TbPrdProduccion tbPrdProduccion = (TbPrdProduccion) iterator.next();
			}
			listaProducciones.setModel(new ListModelList<TbPrdProduccion>(result));
			listaProducciones.setItemRenderer(this);
		} catch (Exception e) {
			ControlMensajes.mensajeError(Labels.getLabel("pacad.mensajeError.noCargaDatos"));
		}
	}
	
	@Override
	public void render(Listitem arg0, Object arg1, int arg2) throws Exception {
		String estiloLink= "color:blue; text-decoration:underline;font-weight:bold";
		
		//Obtengo el objeto pra cada uno de los rows
		TbPrdProduccion pr = (TbPrdProduccion)arg1;
		
		//Creo las celdas correspondientes
		Listcell cellPrd = new Listcell();
		cellPrd.setLabel(pr.getVrNombreproduccion());
		cellPrd.setStyle(estiloLink);
		cellPrd.addEventListener(Events.ON_CLICK, new ProduccionSel(pr));		
		
		Listcell cellTipo = new Listcell();
		cellTipo.setLabel(pr.getTbPrdTipoproduccion().getVrDescripcion());
		cellTipo.setStyle(estiloLink);
		cellTipo.addEventListener(Events.ON_CLICK, new ProduccionSel(pr));	
		
		Listcell cellEstado = new Listcell();
		cellEstado.setLabel(pr.getBlEstado()+"");
		
		Listcell cellFecha = new Listcell();
		cellFecha.setLabel(pr.getDtFechapublicacion().toString());
		
		arg0.appendChild(cellPrd);
		arg0.appendChild(cellTipo);
		arg0.appendChild(cellEstado);
		arg0.appendChild(cellFecha);
	}
		
	/**
	 * Clase que controla el evento de seleccionar la produccón
	 */
	private class ProduccionSel implements EventListener {
		
		private TbPrdProduccion produccion;
		
		public ProduccionSel(TbPrdProduccion produccion){
			this.produccion = produccion;
		}
		
	    public void onEvent(Event event) {
	    	Map a = new HashMap<>();
			a.put("produccion", produccion);
			
			java.io.InputStream zulInput = this.getClass().getClassLoader().getResourceAsStream("com/proint1/udea/produccion/vista/detalleProduccion.zul") ;
			java.io.Reader zulReader = new java.io.InputStreamReader(zulInput);
			
			try {
				Window window;
				Div divCenter = VistasZk.obtenerDivCenter(reporte);
				if (divCenter == null  ){
					System.err.println("no se encontro el div");
				}else{
					System.err.println("SE encontro el div");
				}
								
				divCenter.getChildren().clear();
				window= (Window)Executions.createComponentsDirectly(zulReader,"zul",divCenter,a) ;	
				window.doEmbedded();
			} catch (IOException e) {
				System.err.println("ERROR ENVIANDO");
				e.printStackTrace();
			}
	    }
	}
	
	/**
	 * Metodos Set y Get para los beans.
	 *
	 */
	public ProduccionService getProduccionService() {
		return produccionService;
	}


	public void setProduccionService(ProduccionService produccionService) {
		this.produccionService = produccionService;
	}

	public TbPrdProduccion getProduccionSeleccionada() {
		return produccionSeleccionada;
	}

	public void setProduccionSeleccionada(TbPrdProduccion produccionSeleccionada) {
		this.produccionSeleccionada = produccionSeleccionada;
	}

	public AutorService getAutorService() {
		return autorService;
	}

	public void setAutorService(AutorService autorService) {
		this.autorService = autorService;
	}
	
	
	
	
	
	

	
	
	
}


