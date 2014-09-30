package com.proint1.udea.produccion.ctl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zhtml.Button;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.ext.Selectable;

import com.proint1.udea.administracion.entidades.terceros.Persona;
import com.proint1.udea.produccion.dto.MiembrosGrupoInvestigacion;
import com.proint1.udea.produccion.entidades.TbPrdAutor;
import com.proint1.udea.produccion.entidades.TbPrdGrupoinvestigacion;
import com.proint1.udea.produccion.entidades.TbPrdMiembrosxgrupo;
import com.proint1.udea.produccion.ngc.AutorService;
import com.proint1.udea.produccion.ngc.GrupoInvestigacionService;
import com.proint1.udea.produccion.ngc.PersonaService;
import com.proint1.udea.produccion.util.ControlMensajes;
import com.proint1.udea.produccion.util.ProduccionBLException;
import com.proint1.udea.produccion.util.ProduccionIWException;

public class gestionGruposInvestigacionCtl extends GenericForwardComposer {
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(gestionGruposInvestigacionCtl.class);
	private TbPrdGrupoinvestigacion grupoSeleccionado;
	
	//Serviccios a usar
	GrupoInvestigacionService grupoInvestigacionService;
	AutorService autorService;
	
	PersonaService personaService;
	
	Button btnBuscarGrupo;
	Listbox listaGrupos;
	private Textbox txtBuscarGrupo;
	
	//Componentes de la vista
	Bandbox bdGrupos;
	Grid gribGrupos;
	Textbox txtNombreGrupo;
	Textbox txtAbreviatura;
	Listbox ltbEstado;
	Listbox listaDirectores;
	Button btnNuevoGrupo;
	Button btnGuardarGrupo;
	Button btnEliminarGrupo;
	
	private TbPrdAutor director;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		List<TbPrdGrupoinvestigacion> result =  grupoInvestigacionService.listar();
		List<TbPrdAutor> lisDirectores =  autorService.listar();
		listaDirectores.setModel(new ListModelList<TbPrdAutor>(lisDirectores));
		listaGrupos.setModel(new ListModelList<TbPrdGrupoinvestigacion>(result));
	}

	public void onClick$btnBuscarGrupo() {
		try {
			String keyword = txtBuscarGrupo.getValue();
		    List<TbPrdGrupoinvestigacion> result =  grupoInvestigacionService.obtenerGrupos(keyword);
		    listaGrupos.setModel(new ListModelList<TbPrdGrupoinvestigacion>(result));
		} catch (Exception e) {
			ControlMensajes.mensajeInformation("No se pudo traer los datos de grupo de investigación");
		}
	}
	
	/**
	 * Detecta la seleccion de uno de los grupos de investigación y carga su informacion
	 * @throws ProduccionIWException
	 */
	public void onSelect$listaGrupos() throws ProduccionIWException  {
		//Detecto la seleccion y la convierto a una clases
		Set<TbPrdGrupoinvestigacion> selection = ((Selectable<TbPrdGrupoinvestigacion>)listaGrupos.getModel()).getSelection();
		TbPrdGrupoinvestigacion grupoSelected = selection.iterator().next();
		this.grupoSeleccionado = grupoSelected;
		
		this.txtNombreGrupo.setText(grupoSelected.getVrNombre());
		this.txtAbreviatura.setText(grupoSelected.getVrAbreviatura());
		this.ltbEstado.setSelectedIndex(1);
	}
	
	/**
	 * Detecta la seleccion de uno de los grupos de investigación y carga su informacion
	 * @throws ProduccionIWException
	 */
	public void onSelect$listaDirectores() throws ProduccionIWException  {
		//Detecto la seleccion y la convierto a una clases
		Set<TbPrdAutor> selection = ((Selectable<TbPrdAutor>)listaDirectores.getModel()).getSelection();
		this.director = selection.iterator().next();
	}
	
	public void onClick$btnNuevoGrupo() {
		/*bdGrupos.setText(null);
		txtNombreGrupo.setText(null);
		txtAbreviatura.setText(null);
		//listaDirectores.clearSelection();
		//ltbIdDirector.clearSelection();
		ltbEstado.clearSelection();
		*/
		List<Listitem> a = listaDirectores.getItems();
		System.out.println("CUENTA -- " + a.size());
		for (Listitem l : a) {
			TbPrdAutor y = l.getValue();
			System.out.println("EN LISTA ------ " + y.getPersona().getNombres());
			//this.listaDirectores.
			if (y.getPersona().getNombres().equals("Edison")){
				l.setSelected(true);
			}
		}
		/*for (Iterator iterator = a.iterator(); iterator.hasNext();) {
			Set<String> salesmen = ((ListModelList<TbPrdAutor>)salesmenModel).ge
			//System.out.println( "-------------*************** " + l.getLabel() );
		}*/
		
	}
	
	public void onClick$btnGuardarGrupo() {
		// ToDo implementar metodo
	}
	
	public void onClick$btnEliminarGrupo() {
		// ToDo implementar metodo
	}
	
	public void onCreate() {
		// ToDo implementar metodo
	}

	public GrupoInvestigacionService getGrupoInvestigacionService() {
		return grupoInvestigacionService;
	}

	public void setGrupoInvestigacionService(
			GrupoInvestigacionService grupoInvestigacionService) {
		this.grupoInvestigacionService = grupoInvestigacionService;
	}
	
	

	public AutorService getAutorService() {
		return autorService;
	}

	public void setAutorService(AutorService autorService) {
		this.autorService = autorService;
	}

	public PersonaService getPersonaService() {
		return personaService;
	}

	public void setPersonaService(PersonaService personaService) {
		this.personaService = personaService;
	}

}
