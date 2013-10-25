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
	private Knot selectedKnot;
	
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
		streets.add(street);
		
		super.setChanged();
		super.notifyObservers(street);
		
	}
	
	public void removeStreet(Street street) {
		streets.remove(street);
		
		super.setChanged();
		super.notifyObservers(street);
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
	
	public Knot getSelectedKnot() {
		return selectedKnot;
	}

	public void setSelectedKnot(Knot selectedKnot) {
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
	

}
