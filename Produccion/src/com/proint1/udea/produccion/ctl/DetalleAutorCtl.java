package com.proint1.udea.produccion.ctl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.image.AImage;
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
import com.proint1.udea.produccion.entidades.TbPrdTipoproduccion;
import com.proint1.udea.produccion.util.ControlMensajes;
import com.proint1.udea.produccion.util.ProduccionBLException;
import com.proint1.udea.produccion.util.ProduccionDAOException;
import com.proint1.udea.produccion.util.ProduccionIWException;
import com.proint1.udea.produccion.util.VistasZk;

public class DetalleAutorCtl extends GenericForwardComposer implements ListitemRenderer {
	
	private static final long serialVersionUID = 1L;
	
	private TbPrdAutor autor;
	
	Label lbNombreAutor;
	Label lbTipoId;
	Label lbNumeroId;
	Label lbNacionalidad;
	Label lbApellidos;
	Label lbNombres;
	Label lbDireccion;
	Label lbEmail;
	Label lbTelefono;
	Image foto;
	
	Listbox listaProducciones;
	
	Window detalleAutor;
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		try{
		this.autor = (TbPrdAutor) arg.get("autor");
		
		}catch (Exception e){
			ControlMensajes.mensajeError("No se recibieron los datos");
		}
	}
	
	public void onCreate() throws ProduccionDAOException, ProduccionBLException, ProduccionIWException {
		this.cargarDatosBasicos();
		this.cargarProducciones();
	}
	
	/**
	 * Carga los datos basicos del formulario
	 */
	private void cargarDatosBasicos(){
		
		this.lbNombreAutor.setValue(this.autor.getPersona().getVrApellidos() + " " + this.autor.getPersona().getVrNombres() );
		this.lbTipoId.setValue(this.autor.getPersona().getTbAdmTipoidentificacion().getVrDescripcion());
		this.lbNumeroId.setValue(this.autor.getPersona().getVrIdentificacion());
		this.lbNacionalidad.setValue(this.autor.getPais().getVrDescripcion());
		this.lbApellidos.setValue(this.autor.getPersona().getVrApellidos());
		this.lbNombres.setValue(this.autor.getPersona().getVrNombres());
		this.lbDireccion.setValue(this.autor.getPersona().getVrDireccion());
		this.lbEmail.setValue(this.autor.getPersona().getVrEmail());
		this.lbTelefono.setValue(this.autor.getPersona().getVrTelefono());
		byte[] imgByte = this.autor.getImagen();
		if(imgByte!=null){	
			try {
				AImage img = new AImage("",imgByte);
				foto.setContent(img);
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			foto.setSrc("/img/no-user-icon.png");
		}
		System.err.println("CARGADOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
	}
	
	private void cargarProducciones(){
		try {
			List<TbPrdProduccion> listaProds = new ArrayList<TbPrdProduccion>();
			Set <TbPrdAutoresxproduccion>producciones =  this.autor.getTbPrdAutoresxproduccions();
			for (TbPrdAutoresxproduccion prodsxautor : producciones) {
				TbPrdProduccion prod = prodsxautor.getTbPrdProduccion();
				listaProds.add(prod);
			}
			
			this.listaProducciones.setModel(new ListModelList<TbPrdProduccion>(listaProds));
			this.listaProducciones.setItemRenderer(this);
		} catch (Exception e) {
			e.printStackTrace();
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
		cellTipo.addEventListener(Events.ON_CLICK, new TipoProduccionSel(pr.getTbPrdTipoproduccion()));	
		
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
				Div divCenter = VistasZk.obtenerDivCenter(detalleAutor);
								
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
	 * Clase que controla el evento de seleccionar la produccón
	 */
	private class TipoProduccionSel implements EventListener {
		
		private TbPrdTipoproduccion tipoProd;
		
		public TipoProduccionSel(TbPrdTipoproduccion tipoProd){
			this.tipoProd = tipoProd;
		}
		
	    public void onEvent(Event event) {
	    	Map a = new HashMap<>();
			a.put("tipoProduccion", tipoProd);
			
			java.io.InputStream zulInput = this.getClass().getClassLoader().getResourceAsStream("com/proint1/udea/produccion/vista/detalleTipoProduccion.zul") ;
			java.io.Reader zulReader = new java.io.InputStreamReader(zulInput);
			
			try {
				Window window;
				Div divCenter = VistasZk.obtenerDivCenter(detalleAutor);
												
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
