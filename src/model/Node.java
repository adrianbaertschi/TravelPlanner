package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Node {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private int x;
	private int y;
	private boolean isStartingPosition;
	private boolean isEndPosition;
	private int height;

	public Node() {}
	
	public Node(int x, int y) {
		this.setX(x);
		this.setY(y);
	}
	
	public Node(int x, int y, int height) {
		this.setX(x);
		this.setY(y);
		this.setHeight(height);
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
	
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Knots are equal if x and y are equal
	 */
	public boolean equals(Object o) {
		if(o instanceof Node) {
			Node k = (Node)o;
			return this.x == k.x && this.y == k.y;
		}
		return false;
	}
}
