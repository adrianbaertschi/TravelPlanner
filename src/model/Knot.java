package model;

import java.awt.Color;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Knot {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private int x;
	private int y;
	private boolean isStartingPosition;
	private boolean isEndPosition;
	private Color color = Color.DARK_GRAY;
	
	public Knot() {}
	
	public Knot(int x, int y) {
		this.setX(x);
		this.setY(y);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public String toString() {
		return "(" + x + "/" + y + ")";
	}

	/**
	 * @return the isStartingPosition
	 */
	public boolean isStartingPosition() {
		return isStartingPosition;
	}

	/**
	 * @param isStartingPosition the isStartingPosition to set
	 */
	public void setStartingPosition(boolean isStartingPosition) {
		this.isStartingPosition = isStartingPosition;
	}

	/**
	 * @return the isFinalPosition
	 */
	public boolean isEndPosition() {
		return isEndPosition;
	}

	/**
	 * @param isFinalPosition the isFinalPosition to set
	 */
	public void setEndPosition(boolean isEndPosition) {
		this.isEndPosition = isEndPosition;
	}
	
	/**
	 * Knots are equal if x and y are equal
	 */
	public boolean equals(Object o) {
		if(o instanceof Knot) {
			Knot k = (Knot)o;
			return this.x == k.x && this.y == k.y;
		}
		return false;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

}
