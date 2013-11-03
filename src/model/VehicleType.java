package model;

import java.awt.Color;
/**
 * 
 */

/**
 * @author dimitri.haemmerli
 *
 */
public enum VehicleType {
	
	REDCAR("redCar", "images/car.jpg"),
    REDFINISH("redFinish", "images/finish"),
    GREENCAR("greenCar", "images/car.jpg"), 
    GREENFINISH("greenFinish", "images/finish");

    private String identifier;
    private String url;

    private VehicleType(String identifier, String url) {
    	this.identifier = identifier;
    	this.url = url;
    }

	/**
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}




}
