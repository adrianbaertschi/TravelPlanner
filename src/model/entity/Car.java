/**
 * 
 */
package model.entity;

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
		
		//DefaultValues
		this.setMaxSpeed(120);
		this.gasConsumptionLow = 7.2f;
		this.gasConsumptionMedium = 5.3f;
		this.gasConsumptionHigh = 4.8f;
		
		
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
