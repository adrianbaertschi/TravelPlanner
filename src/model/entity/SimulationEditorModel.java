/**
 * 
 */
package model.entity;

import java.util.Observable;

import model.UserDisruption;

import common.Constants;

/**
 * @author dimitri.haemmerli
 *
 */
public class SimulationEditorModel extends Observable {
	
	private MapEditorModel mapEditorModel = new MapEditorModel();
	private FleetEditorModel fleetEditorModel= new FleetEditorModel();
	private static int runningSimulations = 0;
	private static SimulationEditorModel model = new SimulationEditorModel();

	private SimulationEditorModel(){
		
	}
	
	public static SimulationEditorModel getInstance() {
		return model;
	}
	
	public static void incRunningSimulations() {
		runningSimulations++;
		System.out.println("Started " + runningSimulations);
	}
	
	public static void decRunningSimulations() {
		runningSimulations--;
		System.out.println("Fertig " + runningSimulations);
		
		if(runningSimulations == 0) {
			model.changed(Constants.SIMULATION_FINISHED);
		}
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
		
		if(o instanceof Vehicle || o instanceof UserDisruption) {
			super.notifyObservers(o);
		} else if(Constants.SIMULATION_FINISHED.equals(o)) {
				super.notifyObservers(o);
			
		} else {
			super.notifyObservers(this); //is this used?

		}
	}






}
