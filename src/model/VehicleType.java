package model;

/**
 * @author dimitri.haemmerli
 *
 */
public enum VehicleType {
	
	REDCAR("redCar", "images/car_red.jpg", "images/car_red_finish.jpg"),
    GREENCAR("greenCar", "images/car_green.jpg", "images/car_green_finish.jpg"), 
    BLUECAR("blueCar", "images/car_blue.jpg", "images/car_blue_finish.jpg"), 
    YELLOWCAR("yellowCar", "images/car_yellow.jpg", "images/car_yellow_finish.jpg");

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
