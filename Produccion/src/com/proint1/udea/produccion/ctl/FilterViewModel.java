package com.proint1.udea.produccion.ctl;

import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;

import com.proint1.udea.produccion.dao.Food;
import com.proint1.udea.produccion.dao.FoodData;

public class FilterViewModel {

	private static final String footerMessage = "A Total of %d Food Items";
    private FoodFilter foodFilter = new FoodFilter();
    List<Food> currentFood = FoodData.getAllFoods();

    public FoodFilter getFoodFilter() {
        return foodFilter;
    }

	public ListModel<Food> getFoodModel() {
		return new ListModelList<Food>(currentFood);
	}
	
	public String getFooter() {
		return String.format(footerMessage, currentFood.size());
	}

    @Command
    @NotifyChange({"foodModel", "footer"})
	public void changeFilter() {
		currentFood = FoodData.getFilterFoods(foodFilter);
	}
}
