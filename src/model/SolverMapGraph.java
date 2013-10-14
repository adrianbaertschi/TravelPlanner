/**
 * 
 */
package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
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
	SimpleWeightedGraph<Knot, DefaultWeightedEdge> swg = new SimpleWeightedGraph<Knot, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	SimpleWeightedGraph<Knot, Street> swgTest = new SimpleWeightedGraph<Knot, Street>(Street.class);
	List<Knot> knots = new ArrayList<Knot>();
	
	Knot startPosition = null;
	Knot endPosition = null;
	int counter = 0;
	List<Street> streets;

	
	public void getShortestPath(MapEditorModel model){
			
		streets = model.getStreets();
		
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
//				swg.addEdge(s.getStart(), s.getEnd());
				
			}

			System.out.println(s.getStart().getX() + " " + s.getStart().getY());
			System.out.println(s.getEnd().getX() + " " +  s.getEnd().getY());
	    	
			if(s.getStart().isStartingPosition()){
				startPosition = s.getStart();
			}
			if(s.getEnd().isEndPosition()){
				endPosition = s.getEnd();
			}
				
	    }
		System.out.println(startPosition.getX() + " " + startPosition.getY());
		System.out.println(endPosition.getX() + " " + endPosition.getY());
		System.out.println(swg.containsVertex(endPosition));
//		if(counter >= 2)
			shortestPathDijkstra();
//		counter++;
	}

	public void shortestPathDijkstra(){
		
//		UndirectedGraph<Knot, DefaultWeightedEdge> ug =
//	            new AsUndirectedGraph<Knot, DefaultWeightedEdge>(dg);
		DijkstraShortestPath<Knot, DefaultWeightedEdge> dsp = new DijkstraShortestPath<Knot, DefaultWeightedEdge>(swg, startPosition, endPosition);
		BellmanFordShortestPath<Knot, DefaultWeightedEdge> bfsp = new BellmanFordShortestPath<Knot, DefaultWeightedEdge>(swg, startPosition);
		System.out.println(bfsp.getCost(endPosition));
		System.out.println(dsp.getPathEdgeList());

		List<DefaultWeightedEdge> shortestPath = dsp.getPathEdgeList();

		int i = 0;
		
		for(Street s1 : streets){
			
			s1.setStreetColor(Color.BLUE);

			
		}
		if (shortestPath != null) {
			for (DefaultWeightedEdge d1 : shortestPath) {

				for (Street s1 : streets) {

					if (swg.getEdgeSource(d1).equals(s1.getStart())
							&& swg.getEdgeTarget(d1).equals(s1.getEnd())) {
						s1.setStreetColor(Color.PINK);
					}
				}

			}
		}
		
		System.out.println("pathlength " + dsp.getPathLength());
	}

}
