package model;

public class Knot{
	
	private int x;
	private int y;
	private boolean isStartingPosition;
	private boolean isEndPosition;
	
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

}
