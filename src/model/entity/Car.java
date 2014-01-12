/**
 * 
 */
package model.entity;

import javax.persistence.Entity;
import javax.persistence.Transient;

import model.config.VehicleType;


/**
 * @author dimitri.haemmerli
 *
 */
@Entity
public class Car extends Vehicle {
	
	private float gasConsumptionLow;
	private float gasConsumptionMedium;
	private float gasConsumptionHigh;
	
	//Statistic
	@Transient
	private double gasUsage;	

	
	public Car(){
		
	}
	
	public Car(VehicleType vehicleType){
		
		
		super.setVehicleTypes(vehicleType);
		
		//DefaultValues
		this.setMaxSpeed(120);
		this.gasConsumptionLow = 7.2f;
		this.gasConsumptionMedium = 5.3f;
		this.gasConsumptionHigh = 4.8f;
		super.setName(super.getVehicleTypes().getDefaultName());
		
		//init value
		this.gasUsage = 0.0;
		
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

	/**
	 * @return the gasUsage
	 */
	public double getGasUsage() {
		return gasUsage;
	}

	/**
	 * @param gasUsage the gasUsage to set
	 */
	public void setGasUsage(double gasUsage) {
		this.gasUsage = gasUsage;
	}

	

}
