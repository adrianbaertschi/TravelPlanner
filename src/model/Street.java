package model;

public class Street {
	private Knot start;
	private Knot end;
	
	public Street(Knot start) {
		this.start = start;
	}
	
	public Street(Knot start, Knot end) {
		this.start = start;
		this.end = end;
	}
	
	public Knot getStart() {
		return start;
	}
	public void setStart(Knot start) {
		this.start = start;
	}
	public Knot getEnd() {
		return end;
	}
	public void setEnd(Knot end) {
		this.end = end;
	}
	public int getLenth() {
		
		int a = start.getX() - end.getX();
		int b = start.getY() - end.getY();
		
		return (int) Math.sqrt(a*a + b*b);
	}
}
