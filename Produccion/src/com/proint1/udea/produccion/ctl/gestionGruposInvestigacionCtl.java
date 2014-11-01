package com.proint1.udea.produccion.ctl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.ext.Selectable;

import com.proint1.udea.administracion.entidades.terceros.TbAdmPersona;
import com.proint1.udea.produccion.entidades.TbPrdAutor;
import com.proint1.udea.produccion.entidades.TbPrdGrupoinvestigacion;
import com.proint1.udea.produccion.ngc.AutorService;
import com.proint1.udea.produccion.ngc.GrupoInvestigacionService;
import com.proint1.udea.produccion.ngc.PersonaService;
import com.proint1.udea.produccion.util.ControlMensajes;
import com.proint1.udea.produccion.util.ProduccionBLException;
import com.proint1.udea.produccion.util.ProduccionDAOException;
import com.proint1.udea.produccion.util.ProduccionIWException;
import com.proint1.udea.produccion.util.VistasZk;

public class gestionGruposInvestigacionCtl extends GenericForwardComposer {
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(gestionGruposInvestigacionCtl.class);
	
	//Serviccios a usar
	GrupoInvestigacionService grupoInvestigacionService;
	AutorService autorService;
	PersonaService personaService;
	
	//Lista con todos los autores que existen en el aplicativo
	private List<TbPrdGrupoinvestigacion> listaGrupos;
	private TbPrdGrupoinvestigacion grupoSeleccionado;
	
	//Componentes del formulario de datos
	Listbox listBoxGrupos;
	
	Textbox txtFiltrarGrupo;
	Textbox txtNombre;
	Textbox txtAbreviatura;
	Radiogroup optEstados; 
	Radio optActivo; 
	Radio optInactivo; 
	Listbox listBoxDirectores;
	Listbox listBoxAuxiliares;
	Bandbox bandDirector;
	Bandbox bandAuxiliar;
	
	Button btnGuardar;
	Button btnActualizar;
	
	public void onCreate() throws ProduccionDAOException, ProduccionBLException, ProduccionIWException {
		this.cargarGrupos();
		this.cargarAutores();
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
	
	
	/**
	 * Metodo que permite filtrar la lista rapida de autores
	 */
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
	 * Carga todos los autores de la aplicacion que podran ser directores o auxiliares
	 * @throws ProduccionIWException: En caso de que existan errores en la carga de los datos
	 */
	private void cargarAutores() throws ProduccionIWException{
		try {
			List<TbPrdAutor> listaAutores = autorService.listar();
			listBoxDirectores.setModel(new ListModelList<TbPrdAutor>(listaAutores));
			listBoxAuxiliares.setModel(new ListModelList<TbPrdAutor>(listaAutores));
		} catch (Exception e) {
			ControlMensajes.mensajeError(Labels.getLabel("pacad.mensajeError.noCargaDatos"));
		}
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
		this.txtNombre.setText(grupo.getVrNombre());
		this.txtAbreviatura.setText(grupo.getVrAbreviatura());
		this.bandDirector.setText(grupo.getTbAdmPersonaByNbDirector().getVrNombres());
		this.bandAuxiliar.setText(grupo.getTbAdmPersonaByNbAuxiliar().getVrNombres());
		
		this.establecerSeleccion(listBoxDirectores, grupo.getTbAdmPersonaByNbDirector());
		this.establecerSeleccion(listBoxAuxiliares, grupo.getTbAdmPersonaByNbAuxiliar());
	
		char estado = grupo.getBlEstado();
		if (estado == 'A'){
			this.optActivo.setSelected(true);
		}else{
			this.optInactivo.setSelected(true);
		}
		
		//Cambio boton guardar por actualizar
		this.btnGuardar.setVisible(false);
		this.btnActualizar.setVisible(true);
	}
	
	/**
	 * Metodo que permite guardar la información del grupo de investigación en la base de datos
	 * @throws WrongValueException Error en las validaciones de la vista
	 * @throws ProduccionBLException Error en los servicios de acceso a datos
	 * @throws ProduccionIWException Error en la recarga de grupos
	 */
	public void onClick$btnGuardar() throws WrongValueException, ProduccionBLException, ProduccionIWException {
		//Validaciones adicionales
		/*if (listBoxDirectores.getSelectedItem() == null){
			throw new WrongValueException(ltbTipoId, Labels.getLabel("pacad.error.tipoIdentificacion"));
		}*/
		
		
		bandDirector.getText();
		bandAuxiliar.getText(); 
		
		TbPrdAutor pDirector = null;
		TbPrdAutor pAuxiliar = null;
		try {
			pDirector = (TbPrdAutor)listBoxDirectores.getSelectedItem().getValue();
		} catch (Exception e) {
			throw new WrongValueException(bandDirector, Labels.getLabel("pacad.error.sinDirector"));
		}
		
		try {
			pAuxiliar = (TbPrdAutor)listBoxAuxiliares.getSelectedItem().getValue();
		} catch (Exception e) {
			throw new WrongValueException(bandAuxiliar, Labels.getLabel("pacad.error.sinAuxiliar"));
		}
		
		char estado =  (optActivo.isChecked())  ? 'A' :'I';
		
		try {
			boolean transaccion = grupoInvestigacionService.insertarGrupoInvestigacion(pAuxiliar.getPersona(), pDirector.getPersona(), txtNombre.getValue(), txtAbreviatura.getValue(), new Date(), estado, "admin", new Date());
			if (transaccion) {
				ControlMensajes.mensajeInformation(Labels.getLabel("pacad.form.guardado.true"));
				this.cargarGrupos();
				this.limpiarCampos();
			}else{
				ControlMensajes.mensajeInformation(Labels.getLabel("pacad.form.guardado.false"));
			}
		} catch (ProduccionDAOException e) {
			ControlMensajes.mensajeInformation(Labels.getLabel("pacad.form.guardado.false"));
		}
	}
	
	/**
	 * Metodo que permite guardar la información del grupo de investigación en la base de datos
	 * @throws WrongValueException Error en las validaciones de la vista
	 * @throws ProduccionBLException Error en los servicios de acceso a datos
	 * @throws ProduccionIWException Error en la recarga de grupos
	 */
	public void onClick$btnActualizar() throws WrongValueException, ProduccionBLException, ProduccionIWException {
		bandDirector.getText();
		bandAuxiliar.getText(); 
		
		TbPrdAutor pDirector = null;
		TbPrdAutor pAuxiliar = null;
		try {
			pDirector = (TbPrdAutor)listBoxDirectores.getSelectedItem().getValue();
			System.err.println("-- DIRECTOR -- " + pDirector.getPersona().getVrNombres());
		} catch (Exception e) {
			throw new WrongValueException(bandDirector, Labels.getLabel("pacad.error.sinDirector"));
		}
		
		try {
			pAuxiliar = (TbPrdAutor)listBoxAuxiliares.getSelectedItem().getValue();
			
		} catch (Exception e) {
			throw new WrongValueException(bandAuxiliar, Labels.getLabel("pacad.error.sinAuxiliar"));
		}
		
		char estado =  (optActivo.isChecked())  ? 'A' :'I';
		
		
		
		try {
			boolean transaccion = grupoInvestigacionService.insertarGrupoInvestigacion(pAuxiliar.getPersona(), pDirector.getPersona(), txtNombre.getValue(), txtAbreviatura.getValue(), new Date(), estado, "admin", new Date());
			if (transaccion) {
				ControlMensajes.mensajeInformation(Labels.getLabel("pacad.form.guardado.true"));
				this.cargarGrupos();
				this.limpiarCampos();
			}else{
				ControlMensajes.mensajeInformation(Labels.getLabel("pacad.form.guardado.false"));
			}
		} catch (ProduccionDAOException e) {
			ControlMensajes.mensajeInformation(Labels.getLabel("pacad.form.guardado.false"));
		}
	}

	/**
	 * Se limpian todos los campos del formulario, para el ingreso de datos nuevos
	 */
	public void onClick$imgAddNew()  {
		this.limpiarCampos();
	}
	
	private void limpiarCampos(){
		VistasZk.limpiarCampoConstraint(txtNombre);
		VistasZk.limpiarCampoConstraint(txtAbreviatura);
		VistasZk.limpiarCampoConstraint(bandAuxiliar);
		VistasZk.limpiarCampoConstraint(bandDirector);
		optActivo.setSelected(true);
		
		this.listBoxGrupos.clearSelection();
		this.listBoxDirectores.clearSelection();
		this.listBoxAuxiliares.clearSelection();
		
		//Cambio boton actualizar por guardar
		this.btnActualizar.setVisible(false);
		this.btnGuardar.setVisible(true);
	}
	
	private void establecerSeleccion(Listbox lis,TbAdmPersona autor){
		for (int i = 0; i < lis.getItemCount(); i++) {
			TbPrdAutor autorTmp = lis.getItemAtIndex(i).getValue();
		    if (autor.getNbIdn() == autorTmp.getPersona().getNbIdn()) {
		    	lis.setSelectedIndex(i);
		    	return;
		    }
		}
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

	public GrupoInvestigacionService getGrupoInvestigacionService() {
		return grupoInvestigacionService;
	}

	public void setGrupoInvestigacionService(
			GrupoInvestigacionService grupoInvestigacionService) {
		this.grupoInvestigacionService = grupoInvestigacionService;
	}
	
	
	
	
	


}
