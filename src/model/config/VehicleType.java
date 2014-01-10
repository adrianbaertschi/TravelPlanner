package model.config;

import java.awt.Color;

/**
 * @author dimitri.haemmerli
 *
 */
public enum VehicleType {
	
	REDCAR("RedCar", 				"images/car_red.png", 				"images/car_red_finish.jpg"),
    GREENCAR("GreenCar", 			"images/car_green.png", 			"images/car_green_finish.jpg"), 
    BLUECAR("BlueCar", 				"images/car_blue.png", 				"images/car_blue_finish.jpg"), 
    YELLOWCAR("YellowCar", 			"images/car_yellow.png", 			"images/car_yellow_finish.jpg"),
    MOUNTAINBIKE("MountainBike", 	"images/bicycle_mountainBike.jpg", 	"images/bicycle_mountainBike_finish.jpg");

	// TODO color attribut?
    private String defaultName;
    private String urlVehicle;
    private String urlFinish;

    private VehicleType(String defaultName, String urlVehicle, String urlFinish) {
    	this.defaultName = defaultName;
    	this.urlVehicle = urlVehicle;
    	this.urlFinish = urlFinish;
    }

	/**
	 * @return the defaultName
	 */
	public String getDefaultName() {
		return defaultName;
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
