package com.proint1.udea.produccion.ctl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;

import com.proint1.udea.produccion.entidades.TbPrdAutor;
import com.proint1.udea.produccion.entidades.TbPrdAutoresxproduccion;
import com.proint1.udea.produccion.entidades.TbPrdProduccion;
import com.proint1.udea.produccion.util.ControlMensajes;
import com.proint1.udea.produccion.util.ProduccionBLException;
import com.proint1.udea.produccion.util.ProduccionDAOException;
import com.proint1.udea.produccion.util.ProduccionIWException;
import com.proint1.udea.produccion.util.VistasZk;

public class DetalleProduccionCtl extends GenericForwardComposer implements ListitemRenderer {
	
	private static final long serialVersionUID = 1L;
	
	private TbPrdProduccion produccion;
	
	
	Label lbTituloProduccion;
	
	Label lbTipoProduccion;
	Label lbEstado;
	Label lbFechaPublicacion;
	Label lbUrl;
	Label lbDoi;
	
	Listbox listaAutores;
	
	Window detalleProduccion;
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		try{
		this.produccion = (TbPrdProduccion) arg.get("produccion");

		}catch (Exception e){
			System.out.println("ERROR RECIBIENDO MAP");
		}
	}
	
	public void onCreate() throws ProduccionDAOException, ProduccionBLException, ProduccionIWException {
		this.cargarDatosBasicos();
		this.cargarAutores();
		
	}
	
	private void cargarDatosBasicos(){
		this.lbTituloProduccion.setValue(this.produccion.getVrNombreproduccion());
		this.lbTipoProduccion.setValue(this.produccion.getTbPrdTipoproduccion().getVrDescripcion());
		this.lbEstado.setValue(this.produccion.getBlEstado()+"");
		this.lbFechaPublicacion.setValue(this.produccion.getDtFechapublicacion().toString());
		this.lbUrl.setValue(this.produccion.getVrUrl());
		this.lbDoi.setValue(this.produccion.getVrDoi());
	}
	
	private void cargarAutores(){
		try {
			List<TbPrdAutor> listaAutors = new ArrayList<TbPrdAutor>();
			
			Set <TbPrdAutoresxproduccion>autores =  this.produccion.getTbPrdAutoresxproduccions();
			for (TbPrdAutoresxproduccion autorsxprods : autores) {
				TbPrdAutor autor = autorsxprods.getTbPrdAutor();
				listaAutors.add(autor);
			}
			
			this.listaAutores.setModel(new ListModelList<TbPrdAutor>(listaAutors));
			this.listaAutores.setItemRenderer(this);
		} catch (Exception e) {
			ControlMensajes.mensajeError(Labels.getLabel("pacad.mensajeError.noCargaDatos"));
		}
	}
	
	@Override
	public void render(Listitem arg0, Object arg1, int arg2) throws Exception {
		String estiloLink= "color:blue; text-decoration:underline;font-weight:bold";
		
		//Obtengo el objeto pra cada uno de los rows
		TbPrdAutor autor = (TbPrdAutor)arg1;
		
		//Creo las celdas correspondientes
		Listcell cellOpc = new Listcell();
		Image img = new Image("/img/eye-icon.png");
		cellOpc.appendChild(img);
		cellOpc.addEventListener(Events.ON_CLICK, new AutorSel(autor));
		
		Listcell cellApellido = new Listcell();
		cellApellido.setLabel(autor.getPersona().getVrApellidos());
		
		Listcell cellNombre = new Listcell();
		cellNombre.setLabel(autor.getPersona().getVrNombres());
		
		Listcell cellTipoId = new Listcell();
		cellTipoId.setLabel(autor.getPersona().getTbAdmTipoidentificacion().getVrDescripcion());
		
		Listcell cellId = new Listcell();
		cellId.setLabel(autor.getPersona().getVrIdentificacion());
		
		Listcell cellEmail = new Listcell();
		cellEmail.setLabel(autor.getPersona().getVrEmail());
			
		arg0.appendChild(cellOpc);
		arg0.appendChild(cellApellido);
		arg0.appendChild(cellNombre);
		arg0.appendChild(cellTipoId);
		arg0.appendChild(cellId);
		arg0.appendChild(cellEmail);
	}
	
	/**
	 * Clase que controla el evento de seleccionar la produccón
	 */
	private class AutorSel implements EventListener {
		
		private TbPrdAutor autor;
		
		public AutorSel(TbPrdAutor autor){
			this.autor = autor;
		}
		
	    public void onEvent(Event event) {
	    	Map a = new HashMap<>();
			a.put("autor", autor);
			
			java.io.InputStream zulInput = this.getClass().getClassLoader().getResourceAsStream("com/proint1/udea/produccion/vista/detalleAutor.zul") ;
			java.io.Reader zulReader = new java.io.InputStreamReader(zulInput);
			
			Window window;
			
			try {
				
				Div divCenter = VistasZk.obtenerDivCenter(detalleProduccion);
				
				divCenter.getChildren().clear();
				window= (Window)Executions.createComponentsDirectly(zulReader,"zul",divCenter,a) ;	
				
				window.doEmbedded();

			} catch (IOException e) {
				System.err.println("ERROR ENVIANDO");
				e.printStackTrace();
			}
	    }
	}
	

}
