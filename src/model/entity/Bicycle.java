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
public class Bicycle extends Vehicle {


	
	public Bicycle(){
		
		//DefaultValues
		this.setMaxSpeed(20);
		
	}
}
