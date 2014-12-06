package com.proint1.udea.produccion.ctl;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.ext.Selectable;

import com.proint1.udea.produccion.entidades.ReporteProduccion;
import com.proint1.udea.produccion.entidades.TbPrdAutoresxproduccion;
import com.proint1.udea.produccion.entidades.TbPrdGrupoinvestigacion;
import com.proint1.udea.produccion.entidades.TbPrdProduccion;
import com.proint1.udea.produccion.ngc.GrupoInvestigacionService;
import com.proint1.udea.produccion.ngc.ProduccionService;
import com.proint1.udea.produccion.util.ControlMensajes;
import com.proint1.udea.produccion.util.ProduccionIWException;

public class ReporteProduccionCtl extends GenericForwardComposer{

	private static Logger logger=Logger.getLogger(ReporteProduccionCtl.class);
	
	Button btnImprimirReporte;
	Listbox ltbGrupoInvest;
	List<TbPrdProduccion> result;
	List<TbPrdGrupoinvestigacion> listaGrupos;
	ProduccionService produccionService;
	GrupoInvestigacionService grupoInvestigacionService;
	TbPrdGrupoinvestigacion grupoSeleccionado;
	
	Datebox dtFechaInicial;
	Datebox dtFechaFinal;
	Iframe framePdf;
	
	
	public void onCreate() throws ProduccionIWException{
		cargarGrupos();
	}

	private void cargarGrupos() throws ProduccionIWException {
		try {
			this.listaGrupos = grupoInvestigacionService.listar();
			ltbGrupoInvest.setModel(new ListModelList<TbPrdGrupoinvestigacion>(this.listaGrupos));
		} catch (Exception e) {
			throw new ProduccionIWException("No se pudo cargar la lista de materias");
		}
	}
	
	public void onSelect$ltbGrupoInvest() {

		Set<TbPrdGrupoinvestigacion> selection = ((Selectable<TbPrdGrupoinvestigacion>) ltbGrupoInvest
				.getModel()).getSelection();
		
		this.grupoSeleccionado = selection.iterator().next();
	}

	public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
         
	}
	
	@SuppressWarnings("resource")
	public void onClick$btnImprimirReporte() throws IOException, JRException{
		Date fi = new Date(dtFechaInicial.getText());
		Date ff = new Date(dtFechaFinal.getText());
		if(fi.getTime() > ff.getTime()){
			ControlMensajes.mensajeError(Labels.getLabel("pacad.mensajeError.errorFechas"));
		}else{
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("NOMBREEMPRESA", "Universidad de Antioquia");
			byte[] contenido = null;
			ArrayList <ReporteProduccion> listaProd = null;
			listaProd = (ArrayList<ReporteProduccion>)produccionService.obtenerValoresReporte(dtFechaInicial.getText(), dtFechaFinal.getText(), grupoSeleccionado);
			
			if(listaProd == null || listaProd.size()<=0){
				ControlMensajes.mensajeError(Labels.getLabel("pacad.mensajeError.noEncuentraDatos"));
			}else{
				contenido = generarInforme(listaProd);
				final AMedia amedia = new AMedia("FirstReport1.pdf", "pdf", "application/pdf", contenido);
				framePdf.setContent(amedia);
			}
		}
	}

	public byte[] generarInforme(ArrayList<ReporteProduccion> lista) throws JRException {
		 	String path = "C:\\Users\\user\\git\\pacad\\PI2Web\\WebContent\\reportes\\reporteProducciones.jasper";
		 	JasperReport reporte = null;
	        reporte = (JasperReport) JRLoader.loadObjectFromFile(path);
	        
	        Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("NOMBREEMPRESA", "Universidad de Antioquia");
			parameters.put("TOTALPRODUCCIONES", ""+lista.size()+"");
			parameters.put("PROYECTO", "Producciones Académicas");
	        JasperPrint jasperPrint = JasperFillManager.fillReport(reporte,parameters, new JRBeanCollectionDataSource(lista));  
	       // JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\Users\\usuario\\Documents\\Reportes\\ProyectoIntegrador2\\simple_report.pdf"); 
	        //JasperExportManager.exportReportToPdfFile(jasperPrint);
	        return JasperExportManager.exportReportToPdf(jasperPrint);
	}

	public ProduccionService getProduccionService() {
		return produccionService;
	}

	public void setProduccionService(ProduccionService produccionService) {
		this.produccionService = produccionService;
	}

	public GrupoInvestigacionService getGrupoInvestigacionService() {
		return grupoInvestigacionService;
	}

	public void setGrupoInvestigacionService(
			GrupoInvestigacionService grupoInvestigacionService) {
		this.grupoInvestigacionService = grupoInvestigacionService;
	}

}
