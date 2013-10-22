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
	
	private MapEditorModel mapEditorModel;
	private FleetEditorModel fleetEditorModel;

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

	public void changed(){
		
		super.setChanged();
		super.notifyObservers(this);
	
	}


}
