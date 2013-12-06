/**
 * 
 */
package model.simulation;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

import model.SimulationEditorModel;
import model.UserDisruption;
import model.config.SimulationOption;
import model.entity.Car;
import model.entity.Node;
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
	
	public SolverMapGraph(SimulationEditorModel simulationEditorModel){
		
		this.simulationEditorModel = simulationEditorModel;
		
	}
	
	private void startSimulation() {
			
			//TODO: abfangen falls es keinen k�zesten pfad gibt
		
			SimpleDirectedWeightedGraph<Node, DefaultWeightedEdge> swg = createMapGraph();
			int speedLimit = 0;
			
			Queue<Node> pathForVehicle = getPathForVehicle(vehicle, swg);
			
			System.out.println(pathForVehicle);
			
			while(!vehicle.getCurrentKnot().equals(vehicle.getFinishKnot())){
				
							
				//getting the nextKnot from Dijkstra
				vehicle.setNextKnot(pathForVehicle.poll());
				
				for(Street s : simulationEditorModel.getMapEditorModel().getStreets() ){
					if(s.getStart().equals(vehicle.getCurrentKnot()) && s.getEnd().equals(vehicle.getNextKnot()) ||
							s.getStart().equals(vehicle.getNextKnot()) && s.getEnd().equals(vehicle.getCurrentKnot())){
						speedLimit = s.getStreetType().getSpeedLimit();
					}
				}
				
					try {
						driveFromTo(vehicle.getCurrentKnot(), vehicle.getNextKnot(), speedLimit);
					} catch (InterruptedException e) {
						return;
					}
					vehicle.setCurrentKnot(vehicle.getNextKnot());
					
			}
			
			//reinitialize the currentKnot so a new simulation can be performed
			vehicle.setCurrentKnot(vehicle.getStartKnot());

		
	}
	
	private void driveFromTo(Node from, Node to, int speedLimit) throws InterruptedException {
		
		
		// TODO: get speed from car
		int speed = 1; 
		float ticks = new Street(from, to).getLenth() / speed;
		
		
		for(int i=1; i<=ticks; i++){
			
			Node currentPosition = new Node();
			currentPosition.setX((int) (from.getX() + (to.getX() - from.getX())*(i*(1/ticks))));
			currentPosition.setY((int) (from.getY() + (to.getY() - from.getY())*(i*(1/ticks))));
			vehicle.setCurrentPosition(currentPosition);
			
			try {
				
				if((vehicle.getMaxSpeed() < speedLimit && vehicle.getMaxSpeed() != 0) || 
						(vehicle.getMaxSpeed() > speedLimit && vehicle.getSimulationOption().equals(SimulationOption.IGNORE_SPEEDLIMIT))){
				
					vehicle.getThread().sleep((2000/vehicle.getMaxSpeed()));
					
				}else{
					
					vehicle.getThread().sleep((2000/speedLimit));

				}
			
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
		
		for (Street s : streets) {

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

	@Override
	public void run() {

		try {
			Thread.sleep(vehicle.getDelay()*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		startSimulation();
		
	}
    
	private void updateModelSync() {
		
		simulationEditorModel.changed(vehicle);
				
	}
	
	private void recalculate() {
		this.vehicle.getThread().interrupt();
		
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


}
