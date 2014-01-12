package model.entity;

/**
 * Temporary Street used when for interruption of a running simulation
 *
 */
public class TemporaryStreet extends Street {
	
	/**
	 * Vehicle which can use this street for path finding
	 */
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
