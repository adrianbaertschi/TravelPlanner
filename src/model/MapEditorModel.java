package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class MapEditorModel extends Observable {
	
	private List<Street> streets = new ArrayList<Street>();
	
	private Street selectedStreet;
	private SolverMapGraph smg = new SolverMapGraph();

	public MapEditorModel(){
		
	//for test use only
		Knot k1= new Knot(1, 1);
		k1.setStartingPosition(true);
		Knot k2= new Knot(400, 400);
		k2.setEndPosition(true);		
		streets.add(new Street(k1, new Knot(100, 100)));
		streets.add(new Street(new Knot(300, 300), k2));
	//
		
	}
	
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
		if(streets.size()>5){
			smg.addStreets(streets);
		}
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
