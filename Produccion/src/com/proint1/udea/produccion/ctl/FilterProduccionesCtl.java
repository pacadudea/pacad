package com.proint1.udea.produccion.ctl;

import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;

import com.proint1.udea.produccion.dao.ProducciionesData;
import com.proint1.udea.produccion.entidades.TbPrdProduccion;
import com.proint1.udea.produccion.model.ProduccionesFilter;
import com.proint1.udea.produccion.ngc.ProduccionService;

public class FilterProduccionesCtl {
	
	ProduccionService produccionService;

	private static final String footerMessage = "A Total of %d Food Items";
    private ProduccionesFilter filtroProducciones = new ProduccionesFilter();
    
    List<TbPrdProduccion> result = ProducciionesData.getAllFoods();

    public ProduccionesFilter getFoodFilter() {
        return filtroProducciones;
    }

   
	public ListModel<TbPrdProduccion> getFoodModel() {
		return new ListModelList<TbPrdProduccion>(result);
	}
	
	public String getFooter() {
		return String.format(footerMessage, result.size());
	}

    @Command
    @NotifyChange({"foodModel", "footer"})
	public void changeFilter() {
    		
    	System.out.println("hay cambios");
	}
    
}
