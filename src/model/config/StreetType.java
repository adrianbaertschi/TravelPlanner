package model.config;

import java.awt.Color;

public enum StreetType {
	
	DISTRICT_ROAD(30, new Color(38,4,38)),
    IN_TOWN(50, new Color (111,31,255)),
    OUT_OF_TOWN(80, new Color(255,170,0)), 
    MOTORWAY(100, new Color (0,130,76)),
    FREEWAY(120, new Color(174,14,0));

    private Integer speedLimit;
    private Color color;

    private StreetType(Integer speedLimit, Color color) {
    	this.speedLimit = speedLimit;
    	this.color = color;
    }

    public Integer getSpeedLimit() {
    	return speedLimit;
    }
    
    public Color getColor() {
    	return this.color;
    }
}