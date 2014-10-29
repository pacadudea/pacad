package com.proint1.udea.produccion.ctl;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zhtml.Button;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.ext.Selectable;

import com.proint1.udea.produccion.entidades.TbPrdAutor;
import com.proint1.udea.produccion.entidades.TbPrdGrupoinvestigacion;
import com.proint1.udea.produccion.ngc.AutorService;
import com.proint1.udea.produccion.ngc.GrupoInvestigacionService;
import com.proint1.udea.produccion.ngc.PersonaService;
import com.proint1.udea.produccion.util.ControlMensajes;
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


}
