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
	
	private float gasConsumptionLow;
	private float gasConsumptionMedium;
	private float gasConsumptionHigh;
	
	public Car(){
		
	}

	/**
	 * @return the gasConsumptionLow
	 */
	public float getGasConsumptionLow() {
		return gasConsumptionLow;
	}

	/**
	 * @param gasConsumptionLow the gasConsumptionLow to set
	 */
	public void setGasConsumptionLow(float gasConsumptionLow) {
		this.gasConsumptionLow = gasConsumptionLow;
	}

	/**
	 * @return the gasConsumptionMedium
	 */
	public float getGasConsumptionMedium() {
		return gasConsumptionMedium;
	}

	/**
	 * @param gasConsumptionMedium the gasConsumptionMedium to set
	 */
	public void setGasConsumptionMedium(float gasConsumptionMedium) {
		this.gasConsumptionMedium = gasConsumptionMedium;
	}

	/**
	 * @return the gasConsumptionHigh
	 */
	public float getGasConsumptionHigh() {
		return gasConsumptionHigh;
	}

	/**
	 * @param gasConsumptionHigh the gasConsumptionHigh to set
	 */
	public void setGasConsumptionHigh(float gasConsumptionHigh) {
		this.gasConsumptionHigh = gasConsumptionHigh;
	}

	

}
