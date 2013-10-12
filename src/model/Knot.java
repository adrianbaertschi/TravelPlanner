package model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Knot {
	
	@Id
	private long id;
	private int x;
	private int y;
	
	public Knot() {};
	
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

}
