package model.entity;

/**
 * Temporary Street used when TODO
 * @author adrian
 *
 */
public class TemporaryStreet extends Street {
	
	private Vehicle allowedVehicle;
	
	public TemporaryStreet(Node start, Node end) {
		super(start, end);
	}

	public Vehicle getAllowedVehicle() {
		return allowedVehicle;
	}

	public void setAllowedVehicle(Vehicle vehicle) {
		this.allowedVehicle = vehicle;
	}

}
