package com.proint1.udea.produccion.ctl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;
import org.zkoss.zul.ext.Selectable;

import com.proint1.udea.produccion.entidades.TbPrdAutoresxproduccion;
import com.proint1.udea.produccion.entidades.TbPrdCampos;
import com.proint1.udea.produccion.entidades.TbPrdGrupoinvestigacion;
import com.proint1.udea.produccion.entidades.TbPrdProduccion;
import com.proint1.udea.produccion.entidades.TbPrdTipoproduccion;
import com.proint1.udea.produccion.entidades.TbPrdTipoproduccionesxcampo;
import com.proint1.udea.produccion.ngc.AutoresXProduccionService;
import com.proint1.udea.produccion.ngc.ProduccionService;
import com.proint1.udea.produccion.ngc.TipoProduccionService;
import com.proint1.udea.produccion.util.ControlMensajes;
import com.proint1.udea.produccion.util.ProduccionBLException;
import com.proint1.udea.produccion.util.ProduccionDAOException;
import com.proint1.udea.produccion.util.ProduccionIWException;
import com.proint1.udea.produccion.util.VistasZk;

public class DetalleGrupoXTipoProdCtl extends GenericForwardComposer  implements ListitemRenderer{
	
	private static final long serialVersionUID = 1L;
	
	ProduccionService produccionService;
	TipoProduccionService tipoProduccionService;
	AutoresXProduccionService autoresXProduccionService;
	
	private TbPrdTipoproduccion tipoProduccion;
	private TbPrdGrupoinvestigacion grupoInvestigacion;
	private List<TbPrdAutoresxproduccion> listaAutoresXprod;
	private List<TbPrdProduccion>producciones= new ArrayList<TbPrdProduccion>();
	
	
	Label lbGrupo;
		
	Listbox listboxTiposProd;
	Listbox listboxProducciones;
	
	Window detalleGrupoPorTipos;
	
	
	
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		try{
			this.tipoProduccion = (TbPrdTipoproduccion) arg.get("tipoProduccion");
			this.grupoInvestigacion = (TbPrdGrupoinvestigacion) arg.get("grupoInvestigacion");
		}catch (Exception e){
			ControlMensajes.mensajeError("No se recibieron los datos");
		}
	}
	
	public void onCreate() throws ProduccionDAOException, ProduccionBLException, ProduccionIWException {
		
		this.cargarCampos();
	}
	
	
	private void cargarCampos(){
		this.lbGrupo.setValue(this.grupoInvestigacion.getVrNombre());
		
		List<TbPrdTipoproduccion>tipos = new ArrayList<TbPrdTipoproduccion>();
		/*
		Set<TbPrdAutoresxproduccion> l = this.grupoInvestigacion.getTbPrdAutoresxproduccions();
		System.err.println("2222222222222222222222222222222222222222222222222222222222222222222222");
		for (TbPrdAutoresxproduccion y : l) {
			System.out.println("-- TIPO -- " + y.getTbPrdProduccion().getTbPrdTipoproduccion().getVrDescripcion());
		}
		*/
		
		try{	
		//Llenar el listboxTiposProd
		listaAutoresXprod = autoresXProduccionService.listar(this.grupoInvestigacion);
		
		for(TbPrdAutoresxproduccion autorxPr : listaAutoresXprod){
			if(autorxPr.getTbPrdGrupoinvestigacion().getNbIdn() == this.grupoInvestigacion.getNbIdn()){
				if(autorxPr.getTbPrdProduccion().getTbPrdTipoproduccion().getNbIdn() == this.tipoProduccion.getNbIdn()){
					tipos.add(autorxPr.getTbPrdProduccion().getTbPrdTipoproduccion());
				}
			}
		}
		this.listboxTiposProd.setModel(new ListModelList<TbPrdTipoproduccion>(tipos));
		
		
		} catch (Exception e) {
			ControlMensajes.mensajeError(Labels.getLabel("pacad.mensajeError.noCargaDatos"));
		}
	}
	
	public void onSelect$listboxTiposProd() throws ProduccionIWException  {
		TbPrdTipoproduccion tipoSeleccion = (TbPrdTipoproduccion)((Selectable<TbPrdTipoproduccion>)listboxTiposProd.getModel()).getSelection();
		for(TbPrdAutoresxproduccion autorxPr : listaAutoresXprod){
			if(autorxPr.getTbPrdProduccion().getTbPrdTipoproduccion().getNbIdn() == tipoSeleccion.getNbIdn()){
				producciones.add(autorxPr.getTbPrdProduccion());
				
			}
		}
		this.listboxProducciones.setModel(new ListModelList<TbPrdProduccion>(producciones));
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
				
		Listcell cellFecha = new Listcell();
		cellFecha.setLabel(pr.getDtFechapublicacion().toString());
		
		arg0.appendChild(cellPrd);
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
				Div divCenter = VistasZk.obtenerDivCenter(detalleGrupoPorTipos);
				
				divCenter.getChildren().clear();
				window= (Window)Executions.createComponentsDirectly(zulReader,"zul",divCenter,a) ;	
				window.doEmbedded();
			} catch (IOException e) {
				System.err.println("ERROR ENVIANDO");
				e.printStackTrace();
			}
	    }
	}

	public ProduccionService getProduccionService() {
		return produccionService;
	}

	public void setProduccionService(ProduccionService produccionService) {
		this.produccionService = produccionService;
	}

	public TipoProduccionService getTipoProduccionService() {
		return tipoProduccionService;
	}

	public void setTipoProduccionService(TipoProduccionService tipoProduccionService) {
		this.tipoProduccionService = tipoProduccionService;
	}
}
