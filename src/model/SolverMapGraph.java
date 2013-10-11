/**
 * 
 */
package model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.BellmanFordShortestPath;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 * @author dimitri.haemmerli
 *
 */
public class SolverMapGraph{
	
	DirectedGraph<Knot, DefaultEdge> g =
            new DefaultDirectedGraph<Knot, DefaultEdge>(DefaultEdge.class);
	List<Knot> knots = new ArrayList<Knot>();
	
	Knot startPosition = null;
	Knot endPosition = null;
	int counter = 0;

	
	public void addStreets(List<Street> streets){
			
		
	    for(Street s :streets){
	    	
			// add the vertices
	    	if(!g.containsVertex(s.getStart())){
			    g.addVertex(s.getStart());}
			if(!g.containsVertex(s.getEnd())){
			    g.addVertex(s.getEnd());}


			// add edges to create linking structure
			if(!g.containsEdge(s.getStart(), s.getEnd()))
				g.addEdge(s.getStart(), s.getEnd());

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
		System.out.println(g.containsVertex(endPosition));
//		if(counter >= 2)
			shortestPathDijkstra();
//		counter++;
	}

	public void shortestPathDijkstra(){
		
		DijkstraShortestPath<Knot, DefaultEdge> dsp = new DijkstraShortestPath<Knot, DefaultEdge>(g, startPosition, endPosition);
		BellmanFordShortestPath<Knot, DefaultEdge> bfsp = new BellmanFordShortestPath<Knot, DefaultEdge>(g, startPosition);
		System.out.println(bfsp.getCost(endPosition));
		System.out.println(dsp.getPathEdgeList());
		
		System.out.println("pathlength " + dsp.getPathLength());
	}

}
