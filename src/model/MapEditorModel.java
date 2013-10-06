package model;

import java.util.ArrayList;
import java.util.List;

public class MapEditorModel {
	
	private List<Street> streets = new ArrayList<Street>();
	private Street selectedStreet;

	public List<Street> getStreets() {
		return streets;
	}

	public void setStreets(List<Street> streets) {
		this.streets = streets;
	}
	
	public void addStreet(Street street) {
		streets.add(street);
	}

	public void setSelectedStreet(Street selectedStreet) {
		this.selectedStreet = selectedStreet;
	}
	
	public Street getSelectedStreet() {
		return selectedStreet;
	}
	

}
