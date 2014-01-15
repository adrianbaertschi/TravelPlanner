package model;

public class NoPathFoundExceptionException extends RuntimeException {

	public NoPathFoundExceptionException(String vehicleName) {
		super(String.format("No path exists for vehicle %s, please change Start/Finish positions", vehicleName));
	}

}
