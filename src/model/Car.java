/**
 * 
 */
package model;

import javax.persistence.Entity;


/**
 * @author dimitri.haemmerli
 *
 */
@Entity
public class Car extends Vehicle {
	
	private int gasConsumption;
	
	public Car(){
		
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
