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
	private ArrayList<Car> Cars = new ArrayList<Car>();

	
			
	public void addCar(Car car){
		
		Cars.add(car);
		System.out.println("Car " + car.getName() + " added");
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the cars
	 */
	public ArrayList<Car> getCars() {
		return Cars;
	}

	/**
	 * @param cars the cars to set
	 */
	public void setCars(ArrayList<Car> cars) {
		Cars = cars;
	}

	
}
