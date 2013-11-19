/**
 * 
 */
package model;

import javax.persistence.Entity;
import javax.persistence.Transient;


/**
 * @author dimitri.haemmerli
 *
 */
@Entity
public class Vehicle extends EntityBase {
	
	private VehicleType vehicleTypes;
	private int maxSpeed;
	private Node startKnot;
	private Node currentKnot;
	private Node currentPosition;
	private Node nextKnot;
	private Node finishKnot;
	private Boolean isSelected;
	private Boolean isVisible;
	
	@Transient
	private Thread thread;
	@Transient
	private int simulationOption;

	public Vehicle(){
		
	}

	/**
	 * @return the maxSpeed
	 */
	public int getMaxSpeed() {
		return maxSpeed;
	}

	/**
	 * @param maxSpeed the maxSpeed to set
	 */
	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	/**
	 * @return the startKnot
	 */
	public Node getStartKnot() {
		return startKnot;
	}

	/**
	 * @param startKnot the startKnot to set
	 */
	public void setStartKnot(Node startKnot) {
		this.startKnot = startKnot;
	}

	/**
	 * @return the currentKnot
	 */
	public Node getCurrentKnot() {
		return currentKnot;
	}

	/**
	 * @param currentKnot the currentKnot to set
	 */
	public void setCurrentKnot(Node currentKnot) {
		this.currentKnot = currentKnot;
	}

	/**
	 * @return the finishKnot
	 */
	public Node getFinishKnot() {
		return finishKnot;
	}

	/**
	 * @param finishKnot the finishKnot to set
	 */
	public void setFinishKnot(Node finishKnot) {
		this.finishKnot = finishKnot;
	}


	/**
	 * @return the nextKnot
	 */
	public Node getNextKnot() {
		return nextKnot;
	}

	/**
	 * @param nextKnot the nextKnot to set
	 */
	public void setNextKnot(Node nextKnot) {
		this.nextKnot = nextKnot;
	}

	/**
	 * @return the isSelected
	 */
	public Boolean getIsSelected() {
		return isSelected;
	}

	/**
	 * @param isSelected the isSelected to set
	 */
	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	/**
	 * @return the isVisible
	 */
	public Boolean getIsVisible() {
		return isVisible;
	}

	/**
	 * @param isVisible the isVisible to set
	 */
	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}

	/**
	 * @return the currentPosition
	 */
	public Node getCurrentPosition() {
		return currentPosition;
	}

	/**
	 * @param currentPosition the currentPosition to set
	 */
	public void setCurrentPosition(Node currentPosition) {
		this.currentPosition = currentPosition;
	}

	/**
	 * @return the vehicleTypes
	 */
	public VehicleType getVehicleTypes() {
		return vehicleTypes;
	}

	/**
	 * @param vehicleTypes the vehicleTypes to set
	 */
	public void setVehicleTypes(VehicleType vehicleTypes) {
		this.vehicleTypes = vehicleTypes;
	}

	/**
	 * @return the thread
	 */
	public Thread getThread() {
		return thread;
	}

	/**
	 * @param thread the thread to set
	 */
	public void setThread(Thread thread) {
		this.thread = thread;
	}

	/**
	 * @return the simulationOption
	 */
	public int getSimulationOption() {
		return simulationOption;
	}

	/**
	 * @param simulationOption the simulationOption to set
	 */
	public void setSimulationOption(int simulationOption) {
		this.simulationOption = simulationOption;
	}

	
	
	
	

}
