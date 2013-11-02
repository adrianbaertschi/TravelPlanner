package model;

import java.awt.Color;

public enum StreetType {
	
	QUARTIER(30, Color.LIGHT_GRAY),
    INNERORTS(50, Color.GRAY),
    AUSSERORTS(80, Color.BLACK), 
    AUTOSTRASSE(100, Color.ORANGE),
    AUTOBAHN(120, Color.RED);

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