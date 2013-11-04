/**
 * 
 */
package model;

import java.util.List;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

/**
 * @author dimitri.haemmerli
 *
 */
public class SolverMapGraph implements Runnable{
	
	private SimulationEditorModel simulationEditorModel;
	private Vehicle vehicle;
	
	public SolverMapGraph(SimulationEditorModel simulationEditorModel){
		
		this.simulationEditorModel = simulationEditorModel;
		
	}
	
	public void startSimulation() {
		
//		for(Vehicle v : simulationEditorModel.getFleetEditorModel().getVehicles()){
			
			
			//abfangen falls es keinen k�zesten pfad gibt
			SimpleWeightedGraph<Node, DefaultWeightedEdge> swg = createMapGraph();
			int speedLimit = 0;
			while(!vehicle.getCurrentKnot().equals(vehicle.getFinishKnot())){
				
							
				//getting the nextKnot from Dijkstra
				vehicle.setNextKnot(shortestPathDijkstra(vehicle, swg, vehicle.getCurrentKnot(), vehicle.getFinishKnot()));
				for(Street s : simulationEditorModel.getMapEditorModel().getStreets() ){
					if(s.getStart().equals(vehicle.getCurrentKnot()) && s.getEnd().equals(vehicle.getNextKnot()) ||
							s.getStart().equals(vehicle.getNextKnot()) && s.getEnd().equals(vehicle.getCurrentKnot())){
						speedLimit = s.getStreetType().getSpeedLimit();
					}
				}
				
				//drawing the movement to the next knot
				
				while(!vehicle.getCurrentKnot().equals(vehicle.getNextKnot())){
					
					// TODO: get speed from car
					int speed = 1; 
					float ticks = new Street(vehicle.getCurrentKnot(), vehicle.getNextKnot()).getLenth() / speed;
					
					
					for(int i=1; i<=ticks; i++){
						
						Node currentPosition = new Node();
						currentPosition.setX((int) (vehicle.getCurrentKnot().getX() + (vehicle.getNextKnot().getX() - vehicle.getCurrentKnot().getX())*(i*(1/ticks))));
						currentPosition.setY((int) (vehicle.getCurrentKnot().getY() + (vehicle.getNextKnot().getY() - vehicle.getCurrentKnot().getY())*(i*(1/ticks))));
						vehicle.setCurrentPosition(currentPosition);
						
						try {
							Thread.sleep(5 * (150-speedLimit)/10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						updateModelSync();
//						simulationEditorModel.changed(vehicle);

					}
					
					vehicle.setCurrentKnot(vehicle.getNextKnot());
				}
			}

			//reinitialize the currentKnot so a new simulation can be performed
			vehicle.setCurrentKnot(vehicle.getStartKnot());

//		}
		
	}
	
	private SimpleWeightedGraph<Node, DefaultWeightedEdge> createMapGraph(){
			
		SimpleWeightedGraph<Node, DefaultWeightedEdge> swg = new SimpleWeightedGraph<Node, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		List<Street> streets = simulationEditorModel.getMapEditorModel().getStreets();
		
	    for(Street s :streets){
	    	
			// add the vertices
	    	if(!swg.containsVertex(s.getStart())){
	    		swg.addVertex(s.getStart());}
			if(!swg.containsVertex(s.getEnd())){
				swg.addVertex(s.getEnd());}


			// add edges to create linking structure
			if(!swg.containsEdge(s.getStart(), s.getEnd())){
				DefaultWeightedEdge dwg = swg.addEdge(s.getStart(), s.getEnd());			
				swg.setEdgeWeight(dwg, s.getLenth());
				
			}
	    }
		return swg;
	}

	public Node shortestPathDijkstra(Vehicle v, SimpleWeightedGraph<Node, DefaultWeightedEdge> swg, Node currentPosition, Node endPosition){
		
		System.out.println("test");

		DijkstraShortestPath<Node, DefaultWeightedEdge> dsp = new DijkstraShortestPath<Node, DefaultWeightedEdge>(swg, currentPosition, endPosition);
		//BellmanFordShortestPath<Knot, DefaultWeightedEdge> bfsp = new BellmanFordShortestPath<Knot, DefaultWeightedEdge>(swg, startPosition);
		//System.out.println(bfsp.getCost(endPosition));
		System.out.println(dsp.getPathEdgeList());

		List<DefaultWeightedEdge> shortestPath = dsp.getPathEdgeList();
		System.out.println("pathlength " + dsp.getPathLength());

		
		if (shortestPath != null) {
			
			for (DefaultWeightedEdge d1 : shortestPath) {

				// return the nextKnot (could be the source or the target of the
				// edge )
				if (swg.getEdgeSource(d1).equals(v.getCurrentKnot())) {

					return swg.getEdgeTarget(d1);
				}
				if (swg.getEdgeTarget(d1).equals(v.getCurrentKnot())) {

					return swg.getEdgeSource(d1);
				}

			}
		}
		
		return null;
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

		startSimulation();
		
	}
    
	public synchronized void updateModelSync() {
		
		simulationEditorModel.changed(vehicle);
				
	}


}
