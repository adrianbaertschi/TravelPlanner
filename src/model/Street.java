package model;

import java.awt.Color;
import java.awt.geom.Line2D;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Street {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	private Node start;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	private Node end;
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private StreetType streetType;
	
	// TODO: remove from model
	private Color streetColor = Color.BLUE;
	
	public Street() {}
	
	public Street(Node start) {
		this.start = start;
	}
	
	public Street(Node start, Node end) {
		this.start = start;
		this.end = end;
	}
	
	public Node getStart() {
		return start;
	}
	public void setStart(Node start) {
		this.start = start;
	}
	public Node getEnd() {
		return end;
	}
	public void setEnd(Node end) {
		this.end = end;
	}
	
	/**
	 * @return the streetColor
	 */
	public Color getStreetColor() {
		return streetColor;
	}

	/**
	 * @param streetColor the streetColor to set
	 */
	public void setStreetColor(Color streetColor) {
		this.streetColor = streetColor;
	}

	public StreetType getStreetType() {
		return streetType;
	}
	
	public void setStreetType(StreetType streetType) {
		this.streetType = streetType;
	}
	
	
	/**
	 * Calculate length of street based on start, end and heigt
	 * 
	 * @return Lenth of street in px
	 */
	public int getLenth() {
		
		int a = start.getX() - end.getX();
		int b = start.getY() - end.getY();
		
		int c = end.getHeight() - start.getHeight();
		
		return (int) Math.sqrt(a*a + b*b + c*c);
	}
	
	/**
	 * Checks if Point is on street
	 * @param x X-Coordinate
	 * @param y Y-Coordinate
	 * @return true, if selected Point is on the Street, otherwise false 
	 */
	public boolean isPointOnStreet(int x, int y) {
		double distance = Line2D.ptSegDist(start.getX(), start.getY(), end.getX(), end.getY(), x, y);
		distance = Math.abs(distance);
		
		int toleance = 5; //TODO: define constants somewhere
		return distance <= toleance;
		
	}
	
	/**
	 * Calulate incline based on start and end node (45° is 100% -> return 1.0)
	 * 
	 * @return negative if descending, 0 if flat, positive if ascending
	 */
	public double getIncline() {
		return getHeightDiff() / (double) getFlatLength();
	}
	
	/**
	 * Calculate vertical difference from start to end
	 * 
	 * @return vertival difference
	 */
	public int getHeightDiff() {
		return this.end.getHeight() - this.start.getHeight();
	}
	
	/**
	 * Calculate lenth without height (view from top)
	 * 
	 * @return length
	 */
	public int getFlatLength() {
			
			int a = start.getX() - end.getX();
			int b = start.getY() - end.getY();
			
			
			return (int) Math.sqrt(a*a + b*b);
	}
}
