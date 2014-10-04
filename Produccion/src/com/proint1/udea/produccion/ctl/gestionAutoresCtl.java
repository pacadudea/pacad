package com.proint1.udea.produccion.ctl;

import java.awt.Label;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Column;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;

import com.proint1.udea.administracion.entidades.terceros.TbAdmPaises;
import com.proint1.udea.administracion.entidades.terceros.TipoIdentificacion;
import com.proint1.udea.produccion.entidades.TbPrdAutor;
import com.proint1.udea.produccion.entidades.TbPrdGrupoinvestigacion;
import com.proint1.udea.produccion.ngc.impl.AutorServiceImpl;
import com.proint1.udea.produccion.ngc.impl.PaisServiceImpl;
import com.proint1.udea.produccion.ngc.impl.TipoIdentificacionServiceImpl;
import com.proint1.udea.produccion.util.ProduccionBLException;
import com.proint1.udea.produccion.util.ProduccionDAOException;
import com.proint1.udea.produccion.util.ProduccionIWException;

public class gestionAutoresCtl extends GenericForwardComposer {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(gestionAutoresCtl.class);

	AutorServiceImpl autorService; // Atributo que conecta con los service.
	TipoIdentificacionServiceImpl tipoIdentificacionService;
	PaisServiceImpl paisService;

	Listbox listaAutores;	
	Button btnBuscarGrupo;
	
	Listbox ltbTipoId;
	Textbox txtNumeroId;
	Textbox txtPrimerApellido;
	Textbox txtSegundoApellido;
	Textbox txtNombres;
	Textbox txtEmail;
	Textbox txtDireccion;
	Textbox txtTelefono;
	Datebox tbiFechaNacimiento;
	Listbox ltbNacionalidad;
	Radiogroup rdoSexo;
	Button btnGuardarAutor;
	Button btnEliminarAutor;
	Button btnNuevoAutor;

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}

	public void onClick$btnGuardarAutor() throws WrongValueException, NumberFormatException, ProduccionBLException {
	
		autorService.insertar(Long.parseLong(ltbTipoId.getSelectedItem().getValue().toString()), txtNumeroId.getText(), txtNombres.getText(),
				txtPrimerApellido.getText(), txtDireccion.getText(), txtTelefono.getText(), txtEmail.getText(),
				rdoSexo.getSelectedItem().getValue().toString(), new Date(),
				Long.parseLong(ltbNacionalidad.getSelectedItem().getValue().toString()));
		
		/*Messagebox.show((Long.parseLong(ltbTipoId.getSelectedItem().getValue().toString())+ txtNumeroId.getText()+ txtNombres.getText()+
				txtPrimerApellido.getText()+ txtDireccion.getText()+ txtTelefono.getText()+ txtEmail.getText()+
				rdoSexo.getSelectedItem().getValue().toString()+ new Date()+
				Long.parseLong(ltbNacionalidad.getSelectedItem().getValue().toString())));*/
		
		Messagebox.show("Autor guardado correctamente");
	}

	public void onCreate() throws ProduccionDAOException, ProduccionBLException, ProduccionIWException {
		/*logger.info("cargando LISTA DE AUTORES");
		Rows rows = new Rows();
		System.err.println("YESSS ... LOGRAMOS ir");
		List<TbPrdAutor> autores = autorService.listar();
		System.err.println("YESSS ... LOGRAMOS RECIBIR");
		for (TbPrdAutor autor : autores) {
			Row row = new Row();
			row.appendChild(new org.zkoss.zul.Label(autor.getPersona().getVrNombres() + " " + autor.getPersona().getVrApellidos() + ""));
			rows.appendChild(row);
		}
		gridAutores.appendChild(rows);*/
		
		
		try {
			List<TbPrdAutor> result = autorService.listar();
			listaAutores.setModel(new ListModelList<TbPrdAutor>(result));
		} catch (Exception e) {
			throw new ProduccionIWException("No se pudo cargar los elementos iniciales del formulario de autores");
		}
		
		

		// LLENAR LISTBOX TIPOS DE DOCUMETNOS
		List<TipoIdentificacion> tipos = tipoIdentificacionService.listar();
		for (TipoIdentificacion tipo : tipos) {
			ltbTipoId.appendChild(new Listitem(tipo.getDescripcion(), tipo.getIdn()));
		}
		
		// LLENAR LISTBOX PAISES
		List<TbAdmPaises> paises = paisService.listar();
		for (TbAdmPaises pais : paises) {
			ltbNacionalidad.appendChild(new Listitem(pais.getVrDescripcion(), pais.getNbIdn()));
		}
	}

	public void onClick$btnNuevoAutor() {
		ltbTipoId.clearSelection();
		txtNumeroId.setText(null);
		txtPrimerApellido.setText(null);
		txtSegundoApellido.setText(null);
		txtNombres.setText(null);
		txtDireccion.setText(null);
		txtEmail.setText(null);
		txtTelefono.setText(null);
		tbiFechaNacimiento.setValue(null);
		ltbNacionalidad.clearSelection();
		//rdoSexo.
		// lblEdad.set
	}

	public void onClick$btnEliminarAutor() {
		// ToDo
	}

	public void onBlur$txtEmail() {
		if (!(txtEmail.getText().length() == 0)) {
			int cantidadArroba = contarAparicionesCaracter(txtEmail.getText(), '@');
			int cantidadPunto = contarAparicionesCaracter(txtEmail.getText(), '.');
			if (!((cantidadArroba == 1) && ((cantidadPunto > 0) && (cantidadPunto < 3)))) {
				txtEmail.setFocus(true);
				txtEmail.setText(null);
				txtEmail.setPlaceholder("Valor no permitido, intentalo de nuevo");
			} else {
				txtEmail.setPlaceholder("");
			}
		}
	}

	public void onBlur$txtNumeroId() {
		if (!validarSoloNumeros(txtNumeroId.getText())) {
			txtNumeroId.setFocus(true);
			txtNumeroId.setText(null);
			txtNumeroId.setPlaceholder("Valor no permitido, intentalo de nuevo");

		} else {
			txtNumeroId.setPlaceholder("");
		}
	}

	private boolean validarSoloNumeros(String text) {
		for (int i = 0; i < text.length(); i++) {
			if (!(text.charAt(i) > 47 && text.charAt(i) < 58)) {
				return false;
			}
		}
		return true;
	}

	private int contarAparicionesCaracter(String text, char c) {
		int contador = 0;
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) == c) {
				contador++;
			}
		}
		return contador;
	}

	public AutorServiceImpl getAutorService() {
		return autorService;
	}

	public TipoIdentificacionServiceImpl getTipoIdentificacionService() {
		return tipoIdentificacionService;
	}

	public void setTipoIdentificacionService(TipoIdentificacionServiceImpl tipoIdentificacionService) {
		this.tipoIdentificacionService = tipoIdentificacionService;
	}

	public void setAutorService(AutorServiceImpl autorService) {
		this.autorService = autorService;
	}

	public PaisServiceImpl getPaisService() {
		return paisService;
	}

	public void setPaisService(PaisServiceImpl paisService) {
		this.paisService = paisService;
	}

}
