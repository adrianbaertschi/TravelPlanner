/**
 * 
 */
package model.config;

/**
 * @author dimitri.haemmerli
 *
 */
public enum SimulationOption {

	SHORTEST_PATH(1),
	FASTEST_PATH(2),
	LOWEST_GAS_CONSUMPTION(3),
	IGNORE_SPEEDLIMIT(4);
	
	private int simulationOption;
	
	private SimulationOption(int simulationOption){
		
		this.simulationOption = simulationOption;
	}
}
