/**
 * 
 */
package model;


/**
 * @author dimitri.haemmerli
 *
 */
public class Car extends Vehicle {
	
	private int gasConsumption;
	private final String carURL = new String("images/car.jpg");
	
	public Car(){
		
		super.setImageURL(carURL);
	}
	
	/**
	 * @return the gasConsumption
	 */
	public int getGasConsumption() {
		return gasConsumption;
	}


	/**
	 * @param gasConsumption the gasConsumption to set
	 */
	public void setGasConsumption(int gasConsumption) {
		this.gasConsumption = gasConsumption;
	}
	
	

}
