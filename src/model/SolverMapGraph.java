/**
 * 
 */
package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.jgrapht.alg.BellmanFordShortestPath;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

/**
 * @author dimitri.haemmerli
 *
 */
public class SolverMapGraph{
	
//	SimpleWeightedGraph<Knot, Street> swg = new SimpleWeightedGraph<Knot, Street>(Street.class);
	SimpleWeightedGraph<Knot, Street> swgTest = new SimpleWeightedGraph<Knot, Street>(Street.class);
	List<Knot> knots = new ArrayList<Knot>();
	
	Knot startPosition = null;
	Knot endPosition = null;
	int counter = 0;
	List<Street> streets;

	private SimulationEditorModel simulationEditorModel;
	
	public SolverMapGraph(SimulationEditorModel simulationEditorModel){
		
		this.simulationEditorModel = simulationEditorModel;
		
		startSimulation();
		
		
	}
	
	public void startSimulation(){
		
		for(Vehicle v : simulationEditorModel.getFleetEditorModel().getVehicles()){
			
			
			//abfangen falls es keinen küztesten pfad gibt
			
			while(!v.getCurrentKnot().equals(v.getFinishKnot())){
				
							
				//getting the nextKnot from Dijkstra
				v.setNextKnot(shortestPathDijkstra(v, createMapGraph(), v.getCurrentKnot(), v.getFinishKnot()));
				
				
				//drawing the movement to the next knot
				while(!v.getCurrentKnot().equals(v.getNextKnot())){
					
					
					v.setCurrentPosition(new Knot());
					for(int i=10; i>=1; i--){
						
//						System.out.println(v.getNextKnot().getX() - v.getCurrentKnot().getX());
//						System.out.println("Zwischenresult: " + ((v.getNextKnot().getX() - v.getCurrentKnot().getX())/i) + " i: " +i);
//						System.out.println("Result: " + (v.getCurrentKnot().getX() + (v.getNextKnot().getX() - v.getCurrentKnot().getX())/i));
						
						v.getCurrentPosition().setX((v.getCurrentKnot().getX() + (v.getNextKnot().getX() - v.getCurrentKnot().getX())/i));
						v.getCurrentPosition().setY((v.getCurrentKnot().getY() + (v.getNextKnot().getY() - v.getCurrentKnot().getY())/i));
						simulationEditorModel.changed();
						try {
						    Thread.sleep(1000);
						} catch(InterruptedException ex) {
						    Thread.currentThread().interrupt();
						}
						
//						System.out.println("Current Knot " + v.getCurrentKnot().getX() + "  " + v.getCurrentKnot().getY());
//						System.out.println("Next Knot " + v.getNextKnot().getX() + "  " + v.getNextKnot().getY());
//						System.out.println("Current Position " + v.getCurrentPosition().getX() + "  " + v.getCurrentPosition().getY());
					}
					
					v.setCurrentKnot(v.getCurrentPosition());
				}
			}

			
		}
		
	}
	
	public SimpleWeightedGraph createMapGraph(){
			
		SimpleWeightedGraph<Knot, DefaultWeightedEdge> swg = new SimpleWeightedGraph<Knot, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		streets = simulationEditorModel.getMapEditorModel().getStreets();
		
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
//			if(s.getStart().isStartingPosition()){
//				startPosition = s.getStart();
//			}
//			if(s.getEnd().isEndPosition()){
//				endPosition = s.getEnd();
//			}
//				
//	    }
//		System.out.println(startPosition.getX() + " " + startPosition.getY());
//		System.out.println(endPosition.getX() + " " + endPosition.getY());
//		System.out.println(swg.containsVertex(endPosition));
////		if(counter >= 2)
//			shortestPathDijkstra();
////		counter++;
		return swg;
	}

	public Knot shortestPathDijkstra(Vehicle v, SimpleWeightedGraph swg, Knot currentPosition, Knot endPosition){
		
		

		DijkstraShortestPath<Knot, DefaultWeightedEdge> dsp = new DijkstraShortestPath<Knot, DefaultWeightedEdge>(swg, currentPosition, endPosition);
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

					return (Knot) swg.getEdgeTarget(d1);
				}
				if (swg.getEdgeTarget(d1).equals(v.getCurrentKnot())) {

					return (Knot) swg.getEdgeSource(d1);
				}

			}
		}
		
		return null;
	}

}
