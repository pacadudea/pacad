package com.proint1.udea.produccion.ctl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.ext.Selectable;

import com.proint1.udea.produccion.dto.MiembrosGrupoInvestigacion;
import com.proint1.udea.produccion.entidades.TbPrdAutor;
import com.proint1.udea.produccion.entidades.TbPrdGrupoinvestigacion;
import com.proint1.udea.produccion.entidades.TbPrdMiembrosxgrupo;
import com.proint1.udea.produccion.ngc.AutorService;
import com.proint1.udea.produccion.ngc.GrupoInvestigacionService;
import com.proint1.udea.produccion.util.ControlMensajes;
import com.proint1.udea.produccion.util.ProduccionBLException;
import com.proint1.udea.produccion.util.ProduccionDAOException;
import com.proint1.udea.produccion.util.ProduccionException;
import com.proint1.udea.produccion.util.ProduccionIWException;

/**
 * Controlador de la vista para la administración de los Miembros de los grupos de investigación
 * @author Edison
 *
 */
public class GestionMiembrosCtl extends GenericForwardComposer  {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(GestionMiembrosCtl.class);
	private TbPrdGrupoinvestigacion grupoSeleccionado;
		
	//Servicio a usar
	GrupoInvestigacionService grupoInvestigacionService;
	AutorService autorService;
	
	//Componentes de la vista
	Button btnBuscarGrupo;
	Button btnActualizarMiembros;
	Listbox listaGrupos;
	@Wire
    private DualListBoxGrupo dualLBox;
	
	private Textbox txtBuscarGrupo;
	private List<TbPrdMiembrosxgrupo> listaMiembrosSeleccion;
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		List<TbPrdGrupoinvestigacion> result =  grupoInvestigacionService.listar();
		//dualLBox.setModel(new ListModelList<TbPrdGrupoinvestigacion>(result));
	}
	
	/**
	 * Boton buscar por nombre sobre los grupos de ivestigcion
	 * @throws ProduccionBLException
	 */
	public void onClick$btnBuscarGrupo() {
		try {
			String keyword = txtBuscarGrupo.getValue();
		    List<TbPrdGrupoinvestigacion> result =  grupoInvestigacionService.obtenerGrupos(keyword);
		    listaGrupos.setModel(new ListModelList<TbPrdGrupoinvestigacion>(result));
		} catch (Exception e) {
			ControlMensajes.mensajeInformation("No se pudo traer los datos de grupo de investigación");
		}
	}
	
	public void onChange$txtBuscarGrupo() throws ProduccionBLException {
		 this.onClick$btnBuscarGrupo();
	}
	
	/**
	 * Actualiza la lista de miembros pertenecientes a un grupo de investigación
	 * @throws ProduccionIWException
	 */
	public void onClick$btnActualizarMiembros() throws ProduccionIWException  {
		try {
			ListModelList<MiembrosGrupoInvestigacion> seleccionados = dualLBox.getListaFinalMiembros();
			grupoInvestigacionService.actualizarMiembros(this.grupoSeleccionado.getNbIdn(), seleccionados);
			
			ControlMensajes.mensajeInformation("Se actualizaron los miembros del grupo de investigación");
			this.onCreate();
		} catch (Exception e) {
			throw new ProduccionIWException("No se pudo actualizar la lista de miembros del grupo");
		}
	}
	
	/**
	 * Carga inicial de la ventana
	 * @throws ProduccionBLException
	 * @throws ProduccionIWException 
	 */
	public void onCreate() throws ProduccionIWException {
		try {
			List<TbPrdGrupoinvestigacion> result =  grupoInvestigacionService.listar();
	        listaGrupos.setModel(new ListModelList<TbPrdGrupoinvestigacion>(result));
		} catch (Exception e) {
			throw new ProduccionIWException("No se pudo cargar los elementos iniciales de la ventana");
		}
       
	}
	
	/**
	 * Detecta la seleccion de uno de los grupos de investigación y carga las listas de miembros y posibles miembros
	 * @throws ProduccionBLException
	 * @throws ProduccionIWException
	 */
	public void onSelect$listaGrupos() throws ProduccionIWException  {
		//Detecto la seleccion y la convierto a una clases
		Set<TbPrdGrupoinvestigacion> selection = ((Selectable<TbPrdGrupoinvestigacion>)listaGrupos.getModel()).getSelection();
		TbPrdGrupoinvestigacion grupoSelected = selection.iterator().next();
		this.grupoSeleccionado = grupoSelected;
		
		//Convierto resultados a DTO
		List<MiembrosGrupoInvestigacion> listaMiembros = new ArrayList<MiembrosGrupoInvestigacion>();
		listaMiembros.clear();
		for (Iterator iterator = grupoSelected.getTbPrdMiembrosxgrupos().iterator(); iterator.hasNext();) {
			 TbPrdMiembrosxgrupo member = (TbPrdMiembrosxgrupo) iterator.next();
			 
			 MiembrosGrupoInvestigacion miembro = new MiembrosGrupoInvestigacion(new Integer((int)grupoSelected.getNbIdn()), 
					 grupoSelected.getVrNombre(), new Integer((int)member.getTbAdmPersona().getNbIdn()), member.getTbAdmPersona().getVrNombres(), 
					 member.getTbAdmPersona().getVrApellidos());
			 
			 listaMiembros.add(miembro); 
		}
		//Carga la lista de los posibles candidatos a ser miembros
		List<MiembrosGrupoInvestigacion> posiblesMiembros = generarPosiblesMiembros(listaMiembros);
		
		dualLBox.setModel(new ListModelList<MiembrosGrupoInvestigacion>(listaMiembros), 
				 new ListModelList<MiembrosGrupoInvestigacion>(posiblesMiembros));
	}
	
	/**
	 * Metodo que genera la lista de los posibles miembros de un grupo de investigacion
	 * @param listaMiembros lista de los miembros actuales
	 * @return lista de posibles miembros
	 * @throws ProduccionIWException
	 */
	private List<MiembrosGrupoInvestigacion> generarPosiblesMiembros(List<MiembrosGrupoInvestigacion> listaMiembros) throws ProduccionIWException{
		List<MiembrosGrupoInvestigacion> posiblesMiembros = new ArrayList<MiembrosGrupoInvestigacion>();
		try {
			List<TbPrdAutor> autores = autorService.listar();
			System.err.println("SE RECIBIO AUTORES " + autores.size());
			
			for (Iterator iterator = autores.iterator(); iterator.hasNext();) {
				TbPrdAutor autor = (TbPrdAutor) iterator.next();
				
				boolean existe = false; 
				for (Iterator iterator2 = listaMiembros.iterator(); iterator2.hasNext();) {
					MiembrosGrupoInvestigacion m = (MiembrosGrupoInvestigacion) iterator2.next();
					
					if (autor.getId() == m.getIdPersona()){
						existe = true;
					}
				}
				
				if (!existe){
					MiembrosGrupoInvestigacion miembro = new MiembrosGrupoInvestigacion(null, null, new Integer((int)autor.getPersona().getNbIdn()), 
							autor.getPersona().getVrNombres(), autor.getPersona().getVrApellidos());
					
					posiblesMiembros.add(miembro);
				}
			}
		} catch (ProduccionBLException e) {
			throw new ProduccionIWException("No se pudo generar la lista de posibles miembros");
		}
		System.err.println("SE RETORNAN " + posiblesMiembros.size());
		return posiblesMiembros;
	}
	
	
	public TbPrdGrupoinvestigacion getGrupoSeleccionado() {
		return grupoSeleccionado;
	}

	public void setGrupoSeleccionado(TbPrdGrupoinvestigacion grupoSeleccionado) {
		this.grupoSeleccionado = grupoSeleccionado;
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
}
