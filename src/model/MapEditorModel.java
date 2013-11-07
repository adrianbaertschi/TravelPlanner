package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Observable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class MapEditorModel extends Observable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	public long getId() {
		return id;
	}

	@Column(nullable=false)
	private String name;
	
	@OneToMany(cascade=CascadeType.PERSIST)
	private List<Street> streets = new ArrayList<Street>();
	
	@Transient
	private Street selectedStreet;
	
	@Transient
	private Node selectedKnot;
		
	@Column(nullable=false)

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar saveDate;
	

	public MapEditorModel(){
				
	}
	
	public List<Street> getStreets() {
		return streets;
	}

	public void setStreets(List<Street> streets) {
		this.streets = streets;
		
		super.setChanged();
		super.notifyObservers(streets);
	}
	
	public void addStreet(Street street) {
		String message = "Double streets not allowed!";
		for(Street s : streets) {
			if(s.getStart().equals(street.getStart()) && s.getEnd().equals(street.getEnd())) {
				throw new MapEditorModelException(message);
			}
			if(s.getStart().equals(street.getEnd()) && s.getEnd().equals(street.getStart())) {
				throw new MapEditorModelException(message);
			}
		}
		
		
		streets.add(street);
		
		super.setChanged();
		super.notifyObservers(street);
		
	}
	
	public void removeStreet(Street street) {
		streets.remove(street);
		
		super.setChanged();
		super.notifyObservers();
	}

	public void setSelectedStreet(Street selectedStreet) {
		this.selectedStreet = selectedStreet;
		
		super.setChanged();
		super.notifyObservers(selectedStreet);
	}
	
	public Street getSelectedStreet() {
		return selectedStreet;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Node getSelectedKnot() {
		return selectedKnot;
	}

	public void setSelectedKnot(Node selectedKnot) {
		this.selectedKnot = selectedKnot;
		
		super.setChanged();
		super.notifyObservers(selectedKnot);
	}
	
	public Calendar getSaveDate() {
		return saveDate;
	}

	public void setSaveDate(Calendar saveDate) {
		this.saveDate = saveDate;
	}

	public void reset() {
		this.streets = new ArrayList<Street>();
		this.selectedStreet = null;
		this.name = null;
		
		super.setChanged();
		super.notifyObservers();
	}
	
	public void loadModel(MapEditorModel model) {
		this.streets = model.streets;
		this.name = model.name;
		this.id = model.id;
		
		super.setChanged();
		super.notifyObservers();
	}
	
//	/**
//	 * Check if there is a Street from n1 to n2 or from n2 to n1
//	 * @param n1
//	 * @param n2
//	 * @return true if there is already a treet, otherwise false;
//	 */
//	public boolean existsStreet(Node n1, Node n2) {
//		for(Street street : streets) {
//			if(street.getStart().equals(n1) && street.getEnd().equals(n2)) {
//				return true;
//			}
//			if(street.getStart().equals(n2) && street.getEnd().equals(n1)) {
//				return true;
//			}
//		}
//		return false;
//	}
	
}
