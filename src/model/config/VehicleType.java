package model.config;

import java.awt.Color;

/**
 * @author dimitri.haemmerli
 *
 */
public enum VehicleType {
	
	REDCAR(Color.RED, "images/car_red.jpg", "images/car_red_finish.jpg"),
    GREENCAR(Color.GREEN, "images/car_green.jpg", "images/car_green_finish.jpg"), 
    BLUECAR(Color.BLUE, "images/car_blue.jpg", "images/car_blue_finish.jpg"), 
    YELLOWCAR(Color.YELLOW, "images/car_yellow.jpg", "images/car_yellow_finish.jpg"),
    MOUNTAINBIKE(Color.BLACK, "images/bicycle_mountainBike.jpg", "images/bicycle_mountainBike_finish.jpg");

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
