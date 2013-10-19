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
	
	private MapEditorModel mem;

	public SimulationEditorModel(){
		
	}

	/**
	 * @return the mem
	 */
	public MapEditorModel getMapEditorModel() {
		return mem;
	}

	/**
	 * @param mem the mem to set
	 */
	public void setMapEditorModel(MapEditorModel mem) {
		this.mem = mem;
	}
	

}
