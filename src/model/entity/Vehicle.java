/**
 * 
 */
package model.entity;

import java.util.ArrayDeque;
import java.util.Queue;

import javax.persistence.Entity;
import javax.persistence.Transient;

import model.config.SimulationOption;
import model.config.VehicleType;


/**
 * @author dimitri.haemmerli
 *
 */
@Entity
public class Vehicle extends EntityBase {
	
	private VehicleType vehicleTypes;
	private int maxSpeed;
	private Node startNode;
	private Node currentNode;
	private Node currentPosition;
	private Node nextNode;
	private Node finishNode;
	private Boolean isSelected;
	private Boolean isVisible;
	private SimulationOption simulationOption;
	private int delay;
	
	//statistics
	@Transient
	private double pathLength;	
	@Transient
	private Queue<Node> path;
	@Transient
	private double expectedTime;
	@Transient
	private double actualTime;
	@Transient
	private double actualTimeTemp;

	
	
	@Transient
	private Thread thread;
	
	@Transient
	private int simulationDelay;

	public Vehicle(){
		
		//default value
		this.simulationOption = SimulationOption.SHORTEST_PATH;
		this.delay = 0;
		
		//init values
		this.pathLength = 0.0;
		this.actualTime = 0.0;
		this.path = new ArrayDeque<Node>();
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
	 * @return the startNode
	 */
	public Node getStartNode() {
		return startNode;
	}

	/**
	 * @param startNode the startNode to set
	 */
	public void setStartNode(Node startNode) {
		this.startNode = startNode;
	}

	/**
	 * @return the currentNode
	 */
	public Node getCurrentNode() {
		return currentNode;
	}

	/**
	 * @param currentNode the currentNode to set
	 */
	public void setCurrentNode(Node currentNode) {
		this.currentNode = currentNode;
	}

	/**
	 * @return the finishNode
	 */
	public Node getFinishNode() {
		return finishNode;
	}

	/**
	 * @param finishNode the finishNode to set
	 */
	public void setFinishNode(Node finishNode) {
		this.finishNode = finishNode;
	}


	/**
	 * @return the nextNode
	 */
	public Node getNextNode() {
		return nextNode;
	}

	/**
	 * @param nextNode the nextNode to set
	 */
	public void setNextNode(Node nextNode) {
		this.nextNode = nextNode;
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
	 * @return the simulationOption
	 */
	public SimulationOption getSimulationOption() {
		return simulationOption;
	}

	/**
	 * @param simulationOption the simulationOption to set
	 */
	public void setSimulationOption(SimulationOption simulationOption) {
		this.simulationOption = simulationOption;
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
	 * @return the delay
	 */
	public int getDelay() {
		return delay;
	}

	/**
	 * @param delay the delay to set
	 */
	public void setDelay(int delay) {
		this.delay = delay;
	}

	/**
	 * @return the pathLength
	 */
	public double getPathLength() {
		return pathLength;
	}

	/**
	 * @param pathLength the pathLength to set
	 */
	public void setPathLength(double pathLength) {
		this.pathLength = pathLength;
	}

	/**
	 * @return the simulationDelay
	 */
	public int getSimulationDelay() {
		return simulationDelay;
	}

	/**
	 * @param simulationDelay the simulationDelay to set
	 */
	public void setSimulationDelay(int simulationDelay) {
		this.simulationDelay = simulationDelay;
	}

	/**
	 * @return the path
	 */
	public Queue<Node> getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(Queue<Node> path) {
		this.path = path;
	}
	
	/**
	 * @param node to add to the path
	 */
	public void addNode(Node n){
		path.add(n);
	}

	/**
	 * @return the expectedTime
	 */
	public double getExpectedTime() {
		return expectedTime;
	}

	/**
	 * @param expectedTime the expectedTime to set
	 */
	public void setExpectedTime(double expectedTime) {
		this.expectedTime = expectedTime;
	}

	/**
	 * @return the actualTime
	 */
	public double getActualTime() {
		return actualTime;
	}

	/**
	 * @param actualTime the actualTime to set
	 */
	public void setActualTime(double actualTime) {
		this.actualTime = actualTime;
	}

	/**
	 * @return the actualTimeTemp
	 */
	public double getActualTimeTemp() {
		return actualTimeTemp;
	}

	/**
	 * @param actualTimeTemp the actualTimeTemp to set
	 */
	public void setActualTimeTemp(double actualTimeTemp) {
		this.actualTimeTemp = actualTimeTemp;
	}
	
	public int getDistToNext() {
		if(currentPosition == null) {
			return -1;
		}
		int a = currentPosition.getX() - nextNode.getX();
		int b = currentPosition.getY() - nextNode.getY();
		
		double c = nextNode.getHeight() - currentPosition.getHeight();
		
		return (int) Math.sqrt(a*a + b*b + c*c);
		
//		return 0;
	}
}
