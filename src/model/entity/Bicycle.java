/**
 * 
 */
package model.entity;

import javax.persistence.Entity;

import model.config.VehicleType;


/**
 * @author dimitri.haemmerli
 *
 */
@Entity
public class Bicycle extends Vehicle {

	public Bicycle(){
		
	}
	
	public Bicycle(VehicleType vehicleType){
		
		super.setVehicleTypes(vehicleType);
		super.setName(super.getVehicleTypes().getDefaultName());

		
		//DefaultValues
		this.setMaxSpeed(20);
		
	}
}
