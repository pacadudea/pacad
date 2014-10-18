package com.proint1.udea.produccion.ctl;

import java.awt.Button;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.ext.Selectable;

import com.proint1.udea.administracion.entidades.terceros.TbAdmPersona;
import com.proint1.udea.produccion.entidades.TbPrdAutor;
import com.proint1.udea.produccion.entidades.TbPrdAutoresxproduccion;
import com.proint1.udea.produccion.entidades.TbPrdProduccion;
import com.proint1.udea.produccion.ngc.AutorService;
import com.proint1.udea.produccion.ngc.ProduccionService;
import com.proint1.udea.produccion.util.ProduccionBLException;
import com.proint1.udea.produccion.util.ProduccionIWException;

public class ConsultaInteractivaCtl extends GenericForwardComposer implements ListitemRenderer{
	
	private static final long serialVersionUID = 1L;
	List<TbPrdProduccion> result;
	List<TbAdmPersona> autoresxProduccion;
	List<TbPrdProduccion> produccionesxAutor;
	


	
	
	private static Logger logger=Logger.getLogger(ConsultaInteractivaCtl.class);
	
	private TbPrdProduccion produccionSeleccionada;
	private TbAdmPersona autorSeleccionado;
	
	/**
	 * beans del negocio usados
	 */
	ProduccionService produccionService;
	AutorService autorService;
	
	Button bt1;
	Button bt2;
	Popup popDetallesProduccion;
	Textbox prodd;
	//Campos para filtrar
	Textbox filNombre;
	Textbox filTipoProduccion;
	Textbox filEstado;
	

	Textbox filAutorSeleccionado;
	Textbox filProduccionSeleccionado;
	Textbox txtautorSeleccionado;
	
	/**
	 * Elementos de la Vista
	 */
	Listbox listaProducciones;
	Listbox listaAutoresxProduccion;
	Listbox listaDetallesAutor;
	
	Button btSeleccion;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		System.err.println("CARGANDO VENTANA 2");
		
	}
	
	/**
	 * Carga inicial de la ventana
	 * @throws ProduccionBLException
	 * @throws ProduccionIWException 
	 */
	public void onCreate() throws ProduccionIWException {
		System.err.println("CARGANDO VENTANA");
		try {
			result =  produccionService.listar();
			for (Iterator iterator = result.iterator(); iterator.hasNext();) {
				TbPrdProduccion tbPrdProduccion = (TbPrdProduccion) iterator.next();
				
			}
			listaProducciones.setModel(new ListModelList<TbPrdProduccion>(result));
			listaProducciones.setItemRenderer(this);
		} catch (Exception e) {
			throw new ProduccionIWException("No se pudo cargar los elementos iniciales de la ventana");
		}
       
	}
	
	public void onChange$filProduccionSeleccionado() {
		List<TbPrdProduccion> listaProduccionesFiltrada = new ArrayList<TbPrdProduccion>();
		String filtro = this.filProduccionSeleccionado.getValue().toLowerCase();
		for (TbPrdProduccion produccion : produccionesxAutor) {
			if (produccion.getVrNombreproduccion().toLowerCase().contains(filtro)){
				listaProduccionesFiltrada.add(produccion);
			}
			
		}
		listaDetallesAutor.setModel(new ListModelList<TbPrdProduccion>(listaProduccionesFiltrada));
	}
	
	public void onChange$filAutorSeleccionado() {
		System.err.println("CAMIO......");
		List<TbAdmPersona> listaAutoresFiltrada = new ArrayList<TbAdmPersona>();
		String filtro = this.filAutorSeleccionado.getValue().toLowerCase();
		for (TbAdmPersona autor : autoresxProduccion) {
			if (autor.getVrNombres().toLowerCase().contains(filtro)){
				listaAutoresFiltrada.add(autor);
			}
			
		}
		listaAutoresxProduccion.setModel(new ListModelList<TbAdmPersona>(listaAutoresFiltrada));
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
	public void onSelect$listaProducciones() throws ProduccionIWException  {
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
		this.listaAutoresxProduccion.setModel(new ListModelList<TbAdmPersona>(listaAutores));
		
		popDetallesProduccion.open(this.listaProducciones,"overlap_after");
	}
	
	public void onSelect$listaAutoresxProduccion() throws ProduccionIWException, ProduccionBLException  {
		//Detecto la seleccion y la convierto a una clases
		Set<TbAdmPersona> selection = ((Selectable<TbAdmPersona>)listaAutoresxProduccion.getModel()).getSelection();
		TbAdmPersona autorSelected = selection.iterator().next();
		this.autorSeleccionado = autorSelected;
		this.txtautorSeleccionado.setValue(autorSelected.getVrNombres());
		
		TbPrdAutor autor = this.autorService.bucarPersona(autorSelected.getNbIdn());
		
		Set<TbPrdAutoresxproduccion> set = autor.getTbPrdAutoresxproduccions();
		
		List<TbPrdProduccion> listaProducciones = new ArrayList<TbPrdProduccion>(); 
		for (TbPrdAutoresxproduccion s : set) {
			TbPrdProduccion prod = s.getTbPrdProduccion();
			listaProducciones.add(prod);
		}
		this.produccionesxAutor = listaProducciones;
		this.listaDetallesAutor.setModel(new ListModelList<TbPrdProduccion>(listaProducciones));
		
		popDetallesProduccion.open(this.listaProducciones,"overlap_after");
	}
	
	
	
/*	

	private void llenarListboxTiposProd() throws ProduccionBLException{
		Listitem items;
		Listcell uncell;
		List<TbPrdTipoproduccion> tiposProd = tipoProduccionService.listar();
		logger.info("se obtuvo la lista de tiposProduccion size: "+tiposProd.size());
		for (TbPrdTipoproduccion tipo : tiposProd) {
			logger.info("Estoy agregando al listbox: "+tipo.getVrDescripcion());
			items = new Listitem();
			uncell = new Listcell(tipo.getVrDescripcion());
			uncell.setParent(items);
			uncell.setValue(tipo.getNbIdn());
			items.setParent(ltbTipoProduccion);
	    }
	}
*/
	

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

	@Override
	public void render(Listitem arg0, Object arg1, int arg2) throws Exception {
		// TODO Auto-generated method stub
		TbPrdProduccion pr = (TbPrdProduccion)arg1;
		Listcell cell = new Listcell();
		cell.setLabel(pr.getVrNombreproduccion());
		cell.addEventListener(Events.ON_CLICK, new EventListener() {

			@Override
			public void onEvent(Event arg0) throws Exception {
				System.out.println("en el texto ");
				
			}
		});
		
		Listcell cell2 = new Listcell();
		org.zkoss.zul.Button btn = new org.zkoss.zul.Button();
		btn.addEventListener(Events.ON_CLICK, new EventListener() {

			@Override
			public void onEvent(Event arg0) throws Exception {
				eventoClic();
				
			}
		});
		cell2.appendChild(btn);
		
		Listcell cell3 = new Listcell();
		org.zkoss.zul.Button btn2 = new org.zkoss.zul.Button();
		btn2.addEventListener(Events.ON_CLICK, new EventListener() {

			@Override
			public void onEvent(Event arg0) throws Exception {
				System.out.println("segundo en el boton");
				
			}
		});
		cell3.appendChild(btn2);
		
		
		arg0.appendChild(cell);
		arg0.appendChild(cell2);
		arg0.appendChild(cell3);
		
	}
	
	private void eventoClic(){
		System.out.println("clic en el boton");
	}
	
	
}
