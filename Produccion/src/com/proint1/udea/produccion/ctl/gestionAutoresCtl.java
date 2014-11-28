package com.proint1.udea.produccion.ctl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.ext.Selectable;

import com.proint1.udea.administracion.entidades.terceros.TbAdmPaises;
import com.proint1.udea.administracion.entidades.terceros.TbAdmPersona;
import com.proint1.udea.administracion.entidades.terceros.TbAdmTipoIdentificacion;
import com.proint1.udea.produccion.dto.MiembrosGrupoInvestigacion;
import com.proint1.udea.produccion.entidades.TbPrdAutor;
import com.proint1.udea.produccion.entidades.TbPrdGrupoinvestigacion;
import com.proint1.udea.produccion.entidades.TbPrdMiembrosxgrupo;
import com.proint1.udea.produccion.ngc.impl.AutorServiceImpl;
import com.proint1.udea.produccion.ngc.impl.PaisServiceImpl;
import com.proint1.udea.produccion.ngc.impl.TipoIdentificacionServiceImpl;
import com.proint1.udea.produccion.util.ControlMensajes;
import com.proint1.udea.produccion.util.MediaDTO;
import com.proint1.udea.produccion.util.ProduccionBLException;
import com.proint1.udea.produccion.util.ProduccionDAOException;
import com.proint1.udea.produccion.util.ProduccionIWException;
import com.proint1.udea.produccion.util.UploadMedia;
import com.proint1.udea.produccion.util.Validaciones;
import com.proint1.udea.produccion.util.VistasZk;

/**
 * Clase que controla la vista de Autores
 * 
 * @author Edison
 * 
 */
public class gestionAutoresCtl extends GenericForwardComposer {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(gestionAutoresCtl.class);

	// Servicios a utilizar
	AutorServiceImpl autorService;
	TipoIdentificacionServiceImpl tipoIdentificacionService;
	PaisServiceImpl paisService;

	// Lista con todos los autores que existen en el aplicativo
	private List<TbPrdAutor> listaAutores;
	private TbPrdAutor autorSeleccionado;

	// Componentes del formulario de datos
	Listbox listBoxAutores;
	Listbox ltbTipoId;
	Textbox txtNumeroId;
	Textbox txtApellidos;
	Textbox txtNombres;
	Textbox txtEmail;
	Textbox txtDireccion;
	Textbox txtTelefono;
	Textbox txtFiltrarAutor;
	Listbox ltbNacionalidad;
	Image imgAddNew;
	byte[] imagenEnBytes;

	Button btnGuardar;
	Button btnActualizar;
	Button btnCambiarFoto;
	Button btnGuardarImagen;
	Button btnAProducciones;
	Button btnEliminar;
	
	Image foto;
	
	Window winAutores;

	// Imagen de perfil
	Image imagen;
	Media media;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}

	public void onCreate() throws ProduccionDAOException,
			ProduccionBLException, ProduccionIWException {
		this.cargarAutores();
		this.cargarPaises();
		this.cargarTipoDocumentos();
	}

	/**
	 * Metodo que permite guardar la informaci�n del autor en la base de datos
	 * 
	 * @throws WrongValueException
	 *             Error en las validaciones de la vista
	 * @throws ProduccionBLException
	 *             Error en los servicios de acceso a datos
	 * @throws ProduccionIWException
	 *             Error en la recarga de autores
	 */
	public void onClick$btnGuardar() throws WrongValueException,
			ProduccionBLException, ProduccionIWException {
		// Validaciones adicionales
		if (ltbTipoId.getSelectedItem() == null) {
			throw new WrongValueException(ltbTipoId,
					Labels.getLabel("pacad.error.tipoIdentificacion"));
		}

		if (!Validaciones.isTextoVacio(txtNumeroId.getText())) {
			if (!Validaciones.validarSoloNumeros(txtNumeroId.getText())) {
				throw new WrongValueException(txtNumeroId,
						Labels.getLabel("pacad.error.idenformat"));
			}
		}

		if (ltbNacionalidad.getSelectedItem() == null) {
			throw new WrongValueException(ltbNacionalidad,
					Labels.getLabel("pacad.error.nacionalidad"));
		}
		if (!Validaciones.isTextoVacio(txtEmail.getText())) {
			if (!Validaciones.isEmail(txtEmail.getText())) {
				throw new WrongValueException(txtEmail,
						Labels.getLabel("pacad.error.email"));
			}
		}

		long tipoId = Long.parseLong(ltbTipoId.getSelectedItem().getValue()
				.toString());
		long nacionalidad = Long.parseLong(ltbNacionalidad.getSelectedItem()
				.getValue().toString());

		boolean transaccion = autorService.insertar(tipoId,
				txtNumeroId.getText(), txtApellidos.getText(),
				txtNombres.getText(), txtDireccion.getText(),
				txtEmail.getText(), txtTelefono.getText(), nacionalidad, imagenEnBytes);

		if (transaccion) {
			ControlMensajes.mensajeInformation(Labels
					.getLabel("pacad.form.guardado.true"));
			this.cargarAutores();
			this.limpiarCampos();
		} else {
			ControlMensajes.mensajeError(Labels
					.getLabel("pacad.form.guardado.false"));
		}
	}

	public void onClick$btnAProducciones() {
		Map args = new HashMap<>();
		args.put("autor", autorSeleccionado);
		
		java.io.InputStream zulInput = this.getClass().getClassLoader().getResourceAsStream("com/proint1/udea/produccion/vista/detalleAutor.zul") ;
		java.io.Reader zulReader = new java.io.InputStreamReader(zulInput);
		
		Window window;
		try {
			Div divCenter = VistasZk.obtenerDivCenter(winAutores);
			divCenter.getChildren().clear();
			window= (Window)Executions.createComponentsDirectly(zulReader,"zul",divCenter,args) ;	
			window.doEmbedded();
		} catch (IOException e) {
			System.err.println("ERROR ENVIANDO");
			e.printStackTrace();
		}
	}
	
	public void onClick$btnEliminar(){
		Messagebox.show(Labels.getLabel("pacad.texto.eliminar.pregunta"),Labels.getLabel("pacad.texto.confirmar"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
		    public void onEvent(Event evt) throws InterruptedException, ProduccionIWException {
		        if (evt.getName().equals("onYes")) {
		        	boolean transaccion;
		    		try {
		    			transaccion = autorService.eliminar(autorSeleccionado);
		    			if (transaccion) {
		    				ControlMensajes.mensajeInformation(Labels.getLabel("pacad.mensaje.eliminar.true"));
		    				cargarAutores();
		    				limpiarCampos();
		    			}else{
		    				ControlMensajes.mensajeError(Labels.getLabel("pacad.mensaje.eliminar.false"));
		    			}
		    		} catch (WrongValueException | ProduccionBLException e) {
		    			ControlMensajes.mensajeError(Labels.getLabel("pacad.mensaje.eliminar.false"));
		    		}	
		        }
		    }
		});	
	}
	
	
	
	public void onClick$btnActualizar() throws WrongValueException,
			ProduccionBLException, ProduccionIWException {
		// Validaciones adicionales

		if (!Validaciones.isTextoVacio(txtNumeroId.getText())) {
			if (!Validaciones.validarSoloNumeros(txtNumeroId.getText())) {
				throw new WrongValueException(txtNumeroId,
						Labels.getLabel("pacad.error.idenformat"));
			}
		}

		if (ltbNacionalidad.getSelectedItem() == null) {
			throw new WrongValueException(ltbNacionalidad,
					Labels.getLabel("pacad.error.nacionalidad"));
		}
		if (!Validaciones.isTextoVacio(txtEmail.getText())) {
			if (!Validaciones.isEmail(txtEmail.getText())) {
				throw new WrongValueException(txtEmail,
						Labels.getLabel("pacad.error.email"));
			}
		}

		long tipoId = Long.parseLong(ltbTipoId.getSelectedItem().getValue()
				.toString());
		long nacionalidad = Long.parseLong(ltbNacionalidad.getSelectedItem()
				.getValue().toString());

		boolean transaccion = autorService.actualizar(tipoId,
				txtNumeroId.getText(), txtApellidos.getText(),
				txtNombres.getText(), txtDireccion.getText(),
				txtEmail.getText(), txtTelefono.getText(), nacionalidad, imagenEnBytes,
				this.autorSeleccionado);

		if (transaccion) {
			ControlMensajes.mensajeInformation(Labels
					.getLabel("pacad.form.actualizado.true"));
			this.cargarAutores();
			this.limpiarCampos();
		} else {
			ControlMensajes.mensajeError(Labels
					.getLabel("pacad.form.actualizado.false"));
		}
	}

	/**
	 * Detecta la seleccion de uno de los autores y carga su informacion para
	 * ser modificada
	 * 
	 * @throws ProduccionBLException
	 * @throws ProduccionIWException
	 */
	public void onSelect$listBoxAutores() throws ProduccionIWException {
		Set<TbPrdAutor> selection = ((Selectable<TbPrdAutor>) listBoxAutores
				.getModel()).getSelection();
		this.autorSeleccionado = selection.iterator().next();
		this.llenarDatos();
	}

	/**
	 * Se limpian todos los campos del formulario, para el ingreso de datos
	 * nuevos
	 */
	public void onClick$imgAddNew() {
		this.limpiarCampos();
	}

	private void limpiarCampos() {
		ltbTipoId.clearSelection();
		ltbNacionalidad.clearSelection();
		listBoxAutores.clearSelection();

		limpiarCampoConstraint(txtNumeroId);
		limpiarCampoConstraint(txtApellidos);
		limpiarCampoConstraint(txtNombres);

		txtDireccion.setText(null);
		txtEmail.setText(null);
		txtTelefono.setText(null);
		
		foto.setSrc("/img/no-user-icon.png");

		// Cambio boton actualizar por guardar
		this.btnActualizar.setVisible(false);
		this.btnAProducciones.setVisible(false);
		this.btnEliminar.setVisible(false);
		this.btnGuardar.setVisible(true);
	}

	/**
	 * Metodo para limpiar campo TextBox con Constraint
	 * 
	 * @param textbox
	 */
	private void limpiarCampoConstraint(Textbox textbox) {
		if (textbox.getConstraint() != null) {
			Constraint c = textbox.getConstraint();
			textbox.setConstraint((String) null);
			textbox.setText("");
			textbox.setConstraint(c);
		}
	}

	/**
	 * Metodo que permite filtrar la lista rapida de autores
	 */
	public void onChange$txtFiltrarAutor() {
		List<TbPrdAutor> listaFiltrada = new ArrayList<TbPrdAutor>();
		String filtro = this.txtFiltrarAutor.getValue().toLowerCase();
		for (TbPrdAutor autor : this.listaAutores) {
			if (autor.getPersona().getVrNombres().toLowerCase()
					.contains(filtro)) {
				listaFiltrada.add(autor);
			}
		}
		this.listBoxAutores.setModel(new ListModelList<TbPrdAutor>(
				listaFiltrada));
	}

	/**
	 * Carga todos los autores existentes en la aplicaci�n
	 * 
	 * @throws ProduccionIWException
	 *             : En caso de que existan errores en la carga de los datos
	 */
	private void cargarAutores() throws ProduccionIWException {
		try {
			this.listaAutores = autorService.listar();
			listBoxAutores.setModel(new ListModelList<TbPrdAutor>(
					this.listaAutores));
		} catch (Exception e) {
			throw new ProduccionIWException(
					"No se pudo cargar la lista de autores");
		}
	}

	private void llenarDatos() {
		TbAdmPersona persona = autorSeleccionado.getPersona();

		// Lleno los datos
		this.ltbTipoId.setSelectedIndex((int) persona
				.getTbAdmTipoidentificacion().getNbIdn() - 1);
		this.txtNumeroId.setText(persona.getVrIdentificacion());
		this.ltbNacionalidad.setSelectedIndex((int) autorSeleccionado.getPais()
				.getNbIdn() - 1);
		this.txtApellidos.setText(persona.getVrApellidos());
		this.txtNombres.setText(persona.getVrNombres());

		this.txtDireccion.setText((persona.getVrDireccion() == null) ? ""
				: persona.getVrDireccion());
		this.txtEmail.setText((persona.getVrEmail() == null) ? "" : persona
				.getVrEmail());
		this.txtTelefono.setText((persona.getVrTelefono() == null) ? ""
				: persona.getVrTelefono());
		byte[] imgByte = autorSeleccionado.getImagen();
		if(imgByte!=null){	
			try {
				AImage img = new AImage("", imgByte);
				foto.setContent(img);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		}else{
			foto.setSrc("/img/no-user-icon.png");
		}
		
		//Verifico si el autor tiene producciones	
		if (this.autorSeleccionado.getTbPrdAutoresxproduccions().size()<1){
			this.btnAProducciones.setVisible(false);
			this.btnEliminar.setVisible(true);
		}else{
			this.btnAProducciones.setVisible(true);
			this.btnEliminar.setVisible(false);
		}
		
		// Cambio boton guardar por actualizar
		this.btnActualizar.setVisible(true);
		this.btnGuardar.setVisible(false);
	}

	/**
	 * Carga todos los tipos de documento existentes en la aplicaci�n
	 * 
	 * @throws ProduccionIWException
	 *             : En caso de que existan errores en la carga de los datos
	 */
	private void cargarTipoDocumentos() throws ProduccionIWException {
		try {
			List<TbAdmTipoIdentificacion> tipos = tipoIdentificacionService
					.listar();
			for (TbAdmTipoIdentificacion tipo : tipos) {
				ltbTipoId.appendChild(new Listitem(tipo.getVrDescripcion(),
						tipo.getNbIdn()));
			}
		} catch (Exception e) {
			throw new ProduccionIWException(
					"No se pudo cargar los tipos de identificaci�n");
		}
	}

	/**
	 * Carga todos los paises existentes en la aplicaci�n
	 * 
	 * @throws ProduccionIWException
	 *             : En caso de que existan errores en la carga de los datos
	 */
	private void cargarPaises() throws ProduccionIWException {
		try {
			List<TbAdmPaises> paises = paisService.listar();
			for (TbAdmPaises pais : paises) {
				ltbNacionalidad.appendChild(new Listitem(pais
						.getVrDescripcion(), pais.getNbIdn()));
			}
		} catch (Exception e) {
			throw new ProduccionIWException("No se pudo cargar los paises");
		}
	}

	// Servicios utilizados
	public AutorServiceImpl getAutorService() {
		return autorService;
	}

	public void setAutorService(AutorServiceImpl autorService) {
		this.autorService = autorService;
	}

	public TipoIdentificacionServiceImpl getTipoIdentificacionService() {
		return tipoIdentificacionService;
	}

	public void setTipoIdentificacionService(
			TipoIdentificacionServiceImpl tipoIdentificacionService) {
		this.tipoIdentificacionService = tipoIdentificacionService;
	}

	public PaisServiceImpl getPaisService() {
		return paisService;
	}

	public void setPaisService(PaisServiceImpl paisService) {
		this.paisService = paisService;
	}
	
	public void onUpload$btnGuardarImagen(UploadEvent evt){
		MediaDTO dtoMedia = new MediaDTO();
		Media media = evt.getMedia();
		try {
			dtoMedia = UploadMedia.getMedia(media);
			AImage im = new AImage(dtoMedia.getName(), dtoMedia.getObject());
			imagenEnBytes = dtoMedia.getObject();
			foto.setContent(im);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        
	}
}
