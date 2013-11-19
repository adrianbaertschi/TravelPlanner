/**
 * 
 */
package model;

import java.util.Observable;

/**
 * @author dimitri.haemmerli
 *
 */
public class SimulationEditorModel extends Observable {
	
	private MapEditorModel mapEditorModel = new MapEditorModel();
	private FleetEditorModel fleetEditorModel= new FleetEditorModel();

	public SimulationEditorModel(){
		
	}

	/**
	 * @return the mapEditorModel
	 */
	public MapEditorModel getMapEditorModel() {
		
		return mapEditorModel;
	}

	/**
	 * @param mapEditorModel the mapEditorModel to set
	 */
	public void setMapEditorModel(MapEditorModel mapEditorModel) {
		this.mapEditorModel = mapEditorModel;
	}

	/**
	 * @return the fleetEditorModel
	 */
	public FleetEditorModel getFleetEditorModel() {
		return fleetEditorModel;
	}

	/**
	 * @param fleetEditorModel the fleetEditorModel to set
	 */
	public void setFleetEditorModel(FleetEditorModel fleetEditorModel) {
		this.fleetEditorModel = fleetEditorModel;
	}

	public void changed(Object o){
		
		
		super.setChanged();
		
		if(o instanceof Vehicle){
//			System.out.println("Vehicle");
			super.notifyObservers(o);
		} else if(o instanceof UserDisruption) {
			System.out.println("user disruption");
			super.notifyObservers(o);
		} else{
			super.notifyObservers(this);

		}
	}


}
