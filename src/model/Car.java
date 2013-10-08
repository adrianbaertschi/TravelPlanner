/**
 * 
 */
package model;

/**
 * @author dimitri.haemmerli
 *
 */
public class Car {
	
	private String name;
	private int maxSpeed;
	private int gasConsumption;
	
	
	public Car(String name){
		
		this.name = name;
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
	 * @return the maxSpeed
	 */
	public int getMaxSpeed() {
		return maxSpeed;
	}


	/**
	 * @param maxSpeed the maxSpeed to set
	 */
	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
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
