/**
 * 
 */
package model;

import java.util.ArrayList;

/**
 * @author dimitri.haemmerli
 *
 */
public class FleetEditorModel {
	
	private String name;
	private ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();

	
	public FleetEditorModel(){
		
		//testuse only
		Car c1 = new Car();
		c1.setIsSelected(true);
		c1.setCurrentKnot(new Knot());
		c1.setCurrentPosition(new Knot());
		c1.setNextKnot(new Knot());
		c1.setStartKnot(new Knot());
		c1.setFinishKnot(new Knot());
		vehicles.add(c1);
		
	}


	public void addVehicle(Vehicle vehicle){
		
		vehicles.add(vehicle);
	}
	
	/**
	 * @return the vehicles
	 */
	public ArrayList<Vehicle> getVehicles() {
		return vehicles;
	}


	/**
	 * @param vehicles the vehicles to set
	 */
	public void setVehicles(ArrayList<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}
			


	
}
