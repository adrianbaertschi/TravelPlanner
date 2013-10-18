package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class MapEditorModel extends Observable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	public long getId() {
		return id;
	}

	@Column(unique=true, nullable=false)
	private String name;
	
	@OneToMany(cascade=CascadeType.PERSIST)
	private List<Street> streets = new ArrayList<Street>();
	
	@Transient
	private Street selectedStreet;
	
	@Transient
	private SolverMapGraph smg = new SolverMapGraph();

	public MapEditorModel(){
		
	//for test use only
		Knot k1= new Knot(1, 1);
		k1.setStartingPosition(true);
		Knot k2= new Knot(400, 400);
		k2.setEndPosition(true);		
		streets.add(new Street(k1, new Knot(100, 100)));
		streets.add(new Street(new Knot(300, 300), k2));
	//
		
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
		if(streets.size()>3){
			smg.getShortestPath(this);
		}
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
	
	public void reset() {
		this.streets = new ArrayList<Street>();
		this.selectedStreet = null;
		this.name = null;
		super.setChanged();
		super.notifyObservers();
	}
	

}
