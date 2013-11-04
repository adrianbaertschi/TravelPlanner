/**
 * 
 */
package model;

import java.util.ArrayList;
import java.util.Observable;

/**
 * @author dimitri.haemmerli
 *
 */
public class FleetEditorModel extends Observable{
		
	private ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
	private ArrayList<Vehicle> vehicleSelection = new ArrayList<Vehicle>();
	private int vehicleSelectionPos = 0;

	
	public FleetEditorModel(){
		

		//testuse only
		Car c1 = new Car();
		c1.setIsSelected(true);
		c1.setVehicleTypes(VehicleType.GREENCAR);
		vehicles.add(c1);
		
		createVehicleSelection();
	}


private void createVehicleSelection() {

	//green Car
	Car cg = new Car();
	cg.setVehicleTypes(VehicleType.GREENCAR);
	vehicleSelection.add(cg);
	
	//blue Car
	Car cb = new Car();
	cb.setVehicleTypes(VehicleType.BLUECAR);
	vehicleSelection.add(cb);
	
	//red Car
	Car cr = new Car();
	cr.setVehicleTypes(VehicleType.REDCAR);
	vehicleSelection.add(cr);

	//yellow Car
	Car cy = new Car();
	cy.setVehicleTypes(VehicleType.YELLOWCAR);
	vehicleSelection.add(cy);

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
			
	
	

	/**
	 * @return the vehicleSelection
	 */
	public ArrayList<Vehicle> getVehicleSelection() {
		return vehicleSelection;
	}


	/**
	 * @param vehicleSelection the vehicleSelection to set
	 */
	public void setVehicleSelection(ArrayList<Vehicle> vehicleSelection) {
		this.vehicleSelection = vehicleSelection;
	}

	
	
	/**
	 * @return the vehicleSelectionPos
	 */
	public int getVehicleSelectionPos() {
		return vehicleSelectionPos;
	}


	/**
	 * @param vehicleSelectionPos the vehicleSelectionPos to set
	 */
	public void setVehicleSelectionPos(int vehicleSelectionPos) {
		this.vehicleSelectionPos = vehicleSelectionPos;
	}


	public void increaseVehicleSelectionPos(){
		
		if(vehicleSelectionPos < vehicleSelection.size() - 1 ){
			
			vehicleSelectionPos++;
			
		}else{
			
			vehicleSelectionPos = 0;
		}
		
		changed();
	}
	
	public void decreaseVehicleSelectionPos(){
		
		if(vehicleSelectionPos > 0){
			
			vehicleSelectionPos--;
			
		}else{
			
			vehicleSelectionPos = vehicleSelection.size() - 1;
		}
		
		changed();
	}

	public void changed(){
		
		super.setChanged();
		super.notifyObservers(this);
	
	}

}
