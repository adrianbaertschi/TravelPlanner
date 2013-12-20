/**
 * 
 */
package model.simulation;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

import model.UserDisruption;
import model.config.SimulationOption;
import model.entity.Car;
import model.entity.Node;
import model.entity.SimulationEditorModel;
import model.entity.Street;
import model.entity.Vehicle;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

/**
 * @author dimitri.haemmerli
 *
 */
public class SolverMapGraph implements Runnable, Observer{
	
	private SimulationEditorModel simulationEditorModel;
	private Vehicle vehicle;
	private long start;
	private long end;
	
	public SolverMapGraph(SimulationEditorModel simulationEditorModel){
		
		this.simulationEditorModel = simulationEditorModel;
		
	}
	
	private void startSimulation() throws InterruptedException {
			
			//TODO: abfangen falls es keinen k�zesten pfad gibt
		
			SimpleDirectedWeightedGraph<Node, DefaultWeightedEdge> swg = createMapGraph();
//			int speedLimit = 0;
			
			Queue<Node> pathForVehicle = getPathForVehicle(vehicle, swg);
			pathForVehicle.poll();
			
			//Statistics
			if(vehicle.getPath() == null){
				
				vehicle.setPath(pathForVehicle);
			}//TODO:falls path neu berechnet wurde, das neue teilst�ck hinzuf�gen.
			
			
			System.out.println("path for Vehicle " + pathForVehicle);
			
			while(!vehicle.getCurrentKnot().equals(vehicle.getFinishKnot())){
				
							
				//getting the nextKnot from Dijkstra
				vehicle.setNextKnot(pathForVehicle.poll());
				
				
				Street currentStreet = null;
				for(Street s : simulationEditorModel.getMapEditorModel().getStreets() ){
					if(s.getStart().equals(vehicle.getCurrentKnot()) && s.getEnd().equals(vehicle.getNextKnot()) ||
					   s.getStart().equals(vehicle.getNextKnot()) && s.getEnd().equals(vehicle.getCurrentKnot())){
						currentStreet = s;
					}
				}
				
				
				currentStreet.getVehicles().add(vehicle);
				
				int speedLimit = calculateSpeed(currentStreet);
				System.out.println("Limit: " + speedLimit);
				
				vehicle.setCurrentSpeed(speedLimit);
				
				driveFromTo(vehicle.getCurrentKnot(), vehicle.getNextKnot(), speedLimit);
				
				vehicle.setCurrentKnot(vehicle.getNextKnot());
				currentStreet.getVehicles().remove(vehicle);
					
			}
			
			//reinitialize the currentKnot so a new simulation can be performed
			vehicle.setCurrentKnot(vehicle.getStartKnot());

		
	}
	
	private int calculateSpeed(Street currentStreet) {
		
		int speedLimit = vehicle.getMaxSpeed();
		
		if(!vehicle.getSimulationOption().equals(SimulationOption.IGNORE_SPEEDLIMIT)) {
			if(currentStreet.getStreetType().getSpeedLimit() < speedLimit) {
				speedLimit = currentStreet.getStreetType().getSpeedLimit();
			}
		}
		
		if(currentStreet.isNoPassing()) {
			for(Vehicle others : currentStreet.getVehicles()) {
				speedLimit = Math.min(currentStreet.getStreetType().getSpeedLimit(), others.getMaxSpeed());
			}
		}
		
		return speedLimit;
	}
	
	private void catchUp() {
		// TODO
	}
	
	private void driveFromTo(Node from, Node to, int speedLimit) throws InterruptedException {
		
		System.out.println("Drive from " + from + " to " + to);
		
		float ticks = new Street(from, to).getLenth();
		
		
		for(int i=1; i<=ticks; i++){
			
			Node currentPosition = new Node();
			currentPosition.setX((int) (from.getX() + (to.getX() - from.getX())*(i*(1/ticks))));
			currentPosition.setY((int) (from.getY() + (to.getY() - from.getY())*(i*(1/ticks))));
			vehicle.setCurrentPosition(currentPosition);
			
			//statistics
			end = System.currentTimeMillis();
			vehicle.setActualTime(vehicle.getActualTimeTemp() + (end-start)/1000.0);

			
			try {
				
//				if((vehicle.getMaxSpeed() < speedLimit && vehicle.getMaxSpeed() != 0) || 
//						(vehicle.getMaxSpeed() > speedLimit && vehicle.getSimulationOption().equals(SimulationOption.IGNORE_SPEEDLIMIT))){
//				
//					vehicle.getThread().sleep((2000/vehicle.getMaxSpeed()));
//					
//				}else{
					
					vehicle.getThread().sleep((2000/speedLimit));

//				}
			
			} catch (InterruptedException e) {
				// Re-throw
				throw e;
			}
			updateModelSync();

		}
	}
	
	
	protected SimpleDirectedWeightedGraph<Node, DefaultWeightedEdge> createMapGraph(){
			
		SimpleDirectedWeightedGraph<Node, DefaultWeightedEdge> swg = new SimpleDirectedWeightedGraph<Node, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		List<Street> streets = simulationEditorModel.getMapEditorModel().getStreets();
		
		for(int i = 0; i<streets.size(); i++) {
			Street s = streets.get(i);
			
			if(s.isClosed()) {
				continue;
			}


			// add the vertices
			swg.addVertex(s.getStart());
			swg.addVertex(s.getEnd());

			// add edges to create linking structure
			int weight = 0;
			switch (vehicle.getSimulationOption()) {

			case FASTEST_PATH:

				if(vehicle.getMaxSpeed() < s.getStreetType().getSpeedLimit()){
					weight = s.getLenth() / vehicle.getMaxSpeed();
				} else {
					weight =  s.getLenth() / s.getStreetType().getSpeedLimit();
				}

				break;
				
			case IGNORE_SPEEDLIMIT:
				weight = s.getLenth();
				break;
				
			case LOWEST_GAS_CONSUMPTION:
				
				Car car = (Car)vehicle;
				
				switch (s.getStreetType()) {
				
				case QUARTIER:						
					weight = (int) (car.getGasConsumptionLow()/100 *s.getLenth());
					break;
				case INNERORTS:						
					weight = (int) (car.getGasConsumptionLow()/100 *s.getLenth());
					break;
				case AUSSERORTS:						
					weight = (int) (car.getGasConsumptionMedium()/100 *s.getLenth());
					break;
				case AUTOSTRASSE:						
					weight = (int) (car.getGasConsumptionMedium()/100 *s.getLenth());
					break;
				case AUTOBAHN:						
					weight = (int) (car.getGasConsumptionHigh()/100 *s.getLenth());
					break;
				}
				
				break;
			case SHORTEST_PATH:
				weight = s.getLenth();
				break;
				
			default:
				break;

			}
			
			DefaultWeightedEdge edgeOne = swg.addEdge(s.getStart(), s.getEnd());
			swg.setEdgeWeight(edgeOne, weight);
			
			if(!s.isOneWay()) {
				DefaultWeightedEdge edgeTwo = swg.addEdge(s.getEnd(), s.getStart());
				swg.setEdgeWeight(edgeTwo, weight);
			}

		}
		return swg;
	}

	protected Queue<Node> getPathForVehicle(Vehicle vehicle, SimpleDirectedWeightedGraph<Node, DefaultWeightedEdge> swg) {
		
		DijkstraShortestPath<Node, DefaultWeightedEdge> dsp = new DijkstraShortestPath<Node, DefaultWeightedEdge>(swg, vehicle.getCurrentKnot(), vehicle.getFinishKnot());			
		
		//statistics
		vehicle.setPathLength(dsp.getPathLength());
		
		
		Queue<Node> nodes = new ArrayDeque<Node>();

		if(dsp.getPathEdgeList() == null) {
			//TODO: no path exists
			return nodes;
		}
		
		nodes.add(vehicle.getCurrentKnot());
		
		for(DefaultWeightedEdge egde : dsp.getPathEdgeList()) {

			Node source = swg.getEdgeSource(egde);
			if(!nodes.contains(source)) {
				nodes.add(source);
			}

			Node target = swg.getEdgeTarget(egde);
			if(!nodes.contains(target)) {
				nodes.add(target);
			}
		}
		
		//statistics
		vehicle.setPathLength(dsp.getPathLength());

		return nodes;
	}


	/**
	 * @return the vehicle
	 */
	public Vehicle getVehicle() {
		return vehicle;
	}

	/**
	 * @param vehicle the vehicle to set
	 */
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	private void updateModelSync() {
		
		simulationEditorModel.changed(vehicle);
				
	}
	
	private void recalculate() {
		this.vehicle.getThread().interrupt();
		end = System.currentTimeMillis();
		vehicle.setActualTimeTemp(vehicle.getActualTimeTemp() + ((end-start)/1000.0));
		SimulationEditorModel.incRunningSimulations();
		
		Node temp = vehicle.getCurrentPosition();
		Street s1 = new Street(temp, vehicle.getCurrentKnot());
		s1.setTemporary(true);
		Street s2 = new Street(temp, vehicle.getNextKnot());
		s2.setTemporary(true);
		
		for(Street s : simulationEditorModel.getMapEditorModel().getStreets()) {
			if(s.isPointOnStreet(temp.getX(), temp.getY())) {
				s1.setStreetType(s.getStreetType());
				s2.setStreetType(s.getStreetType());
			}
		}
		
		simulationEditorModel.getMapEditorModel().addStreet(s1);
		simulationEditorModel.getMapEditorModel().addStreet(s2);
		
		//TODO: height etc
		this.vehicle.setCurrentKnot(this.vehicle.getCurrentPosition());
		
		SolverMapGraph smg = new SolverMapGraph(simulationEditorModel);
		smg.setVehicle(vehicle);
		
		this.vehicle.setThread(new Thread(smg));
		this.getVehicle().getThread().start();
		
		
	}

	public void update(Observable o, Object arg) {
		if(arg instanceof UserDisruption) {
			System.out.println("recalculate route");
			this.simulationEditorModel = (SimulationEditorModel) o;
			recalculate();
		}
	}

	public void run() {
		try {
			Thread.sleep(vehicle.getDelay()*1000);
			start = System.currentTimeMillis();
			startSimulation();
			
		} catch (InterruptedException e) {
			// Nothinig to do here
		} finally {
			SimulationEditorModel.decRunningSimulations();
		}
	}



}
