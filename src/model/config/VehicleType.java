package model.config;

import java.awt.Color;

/**
 * @author dimitri.haemmerli
 *
 */
public enum VehicleType {
	
	REDCAR(Color.RED, 			"images/car_red.png", 				"images/car_red_finish.jpg"),
    GREENCAR(Color.GREEN, 		"images/car_green.png", 			"images/car_green_finish.jpg"), 
    BLUECAR(Color.BLUE, 		"images/car_blue.png", 				"images/car_blue_finish.jpg"), 
    YELLOWCAR(Color.YELLOW, 	"images/car_yellow.png", 			"images/car_yellow_finish.jpg"),
    MOUNTAINBIKE(Color.BLACK, 	"images/bicycle_mountainBike.jpg", 	"images/bicycle_mountainBike_finish.jpg");

	// TODO color attribut?
    private Color color;
    private String urlVehicle;
    private String urlFinish;

    private VehicleType(Color color, String urlVehicle, String urlFinish) {
    	this.color = color;
    	this.urlVehicle = urlVehicle;
    	this.urlFinish = urlFinish;
    }

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
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
