package model;

import java.util.ArrayList;
import java.util.List;

public class MapEditorModel {
	
	private List<Street> streets = new ArrayList<Street>();

	public List<Street> getStreets() {
		return streets;
	}

	public void setStreets(List<Street> streets) {
		this.streets = streets;
	}
	
	public void addStreet(Street street) {
		streets.add(street);
	}
	

}
