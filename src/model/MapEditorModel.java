package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class MapEditorModel extends Observable {
	
	private List<Street> streets = new ArrayList<Street>();
	private Street selectedStreet;

	public List<Street> getStreets() {
		return streets;
	}

	public void setStreets(List<Street> streets) {
		this.streets = streets;
		super.setChanged();
		super.notifyObservers(streets);
	}
	
	public void addStreet(Street street) {
		streets.add(street);
		super.setChanged();
		super.notifyObservers(street);
	}

	public void setSelectedStreet(Street selectedStreet) {
		this.selectedStreet = selectedStreet;
		super.setChanged();
		super.notifyObservers(selectedStreet);
	}
	
	public Street getSelectedStreet() {
		return selectedStreet;
	}
	

}
