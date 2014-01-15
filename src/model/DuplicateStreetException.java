package model;

public class DuplicateStreetException extends RuntimeException {

	public DuplicateStreetException() {
		super("Duplucate streets not allowed!");
	}

}
