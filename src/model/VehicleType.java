package model;

/**
 * @author dimitri.haemmerli
 *
 */
public enum VehicleType {
	
	REDCAR("redCar", "images/car_red.jpg", "images/finish"),
    GREENCAR("greenCar", "images/car_green.jpg", "images/finish"), 
    BLUECAR("blueCar", "images/car_blue.jpg", "images/finish"), 
    YELLOWCAR("yellowCar", "images/car_yellow.jpg", "images/finish");

    private String identifier;
    private String urlVehicle;
    private String urlFinish;

    private VehicleType(String identifier, String urlVehicle, String urlFinish) {
    	this.identifier = identifier;
    	this.urlVehicle = urlVehicle;
    	this.urlFinish = urlFinish;
    }

	/**
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * @return the urlVehicle
	 */
	public String getUrlVehicle() {
		return urlVehicle;
	}

	/**
	 * @return the urlFinish
	 */
	public String getUrlFinish() {
		return urlFinish;
	}





}
