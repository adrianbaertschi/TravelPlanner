/**
 * 
 */
package model;

import java.util.ArrayList;
import java.util.List;

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
		
	}
	
	public void startSimulation() {
		
		for(Vehicle v : simulationEditorModel.getFleetEditorModel().getVehicles()){
			
			
			//abfangen falls es keinen k�zesten pfad gibt
			SimpleWeightedGraph<Knot, DefaultWeightedEdge> swg = createMapGraph();
			
			while(!v.getCurrentKnot().equals(v.getFinishKnot())){
				
							
				//getting the nextKnot from Dijkstra
				v.setNextKnot(shortestPathDijkstra(v, swg, v.getCurrentKnot(), v.getFinishKnot()));
				
				
				//drawing the movement to the next knot
				
				while(!v.getCurrentKnot().equals(v.getNextKnot())){
					
					// TODO: get speed from car
					int speed = 5; 
					float ticks = new Street(v.getCurrentKnot(), v.getNextKnot()).getLenth() / speed;
					
					
					for(int i=1; i<=ticks; i++){
						
						Knot currentPosition = new Knot();
						currentPosition.setX((int) (v.getCurrentKnot().getX() + (v.getNextKnot().getX() - v.getCurrentKnot().getX())*(i*(1/ticks))));
						currentPosition.setY((int) (v.getCurrentKnot().getY() + (v.getNextKnot().getY() - v.getCurrentKnot().getY())*(i*(1/ticks))));
						v.setCurrentPosition(currentPosition);
						
						try {
							Thread.sleep(speed);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						simulationEditorModel.changed();
					}
					
					v.setCurrentKnot(v.getNextKnot());
				}
			}

			//reinitialize the currentKnot so a new simulation can be performed
			v.setCurrentKnot(v.getStartKnot());

		}
		
	}
	
	private SimpleWeightedGraph<Knot, DefaultWeightedEdge> createMapGraph(){
			
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
		
		System.out.println("test");

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
