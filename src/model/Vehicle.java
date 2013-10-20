/**
 * 
 */
package model;

import java.awt.Image;

/**
 * @author dimitri.haemmerli
 *
 */
public class Vehicle {
	
	private String type;
	private int maxSpeed;
	private Image image;
	private Knot start;
	private Knot finish;
	private Boolean isSelected;

	public Vehicle(){
		
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the maxSpeed
	 */
	public int getMaxSpeed() {
		return maxSpeed;
	}

	/**
	 * @param maxSpeed the maxSpeed to set
	 */
	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	/**
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * @return the start
	 */
	public Knot getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(Knot start) {
		this.start = start;
	}

	/**
	 * @return the finish
	 */
	public Knot getFinish() {
		return finish;
	}

	/**
	 * @param finish the finish to set
	 */
	public void setFinish(Knot finish) {
		this.finish = finish;
	}

	/**
	 * @return the isSelected
	 */
	public Boolean isSelected() {
		return isSelected;
	}

	/**
	 * @param isSelected the isSelected to set
	 */
	public void setSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	
	
	

}
