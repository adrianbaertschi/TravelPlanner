/**
 * 
 */
package model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jgrapht.DirectedGraph;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.BellmanFordShortestPath;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.AsUndirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 * @author dimitri.haemmerli
 *
 */
public class SolverMapGraph{
	
	DirectedGraph<Knot, DefaultEdge> dg =
            new DefaultDirectedGraph<Knot, DefaultEdge>(DefaultEdge.class);
	List<Knot> knots = new ArrayList<Knot>();
	
	Knot startPosition = null;
	Knot endPosition = null;
	int counter = 0;

	
	public void addStreets(List<Street> streets){
			
		
	    for(Street s :streets){
	    	
			// add the vertices
	    	if(!dg.containsVertex(s.getStart())){
			    dg.addVertex(s.getStart());}
			if(!dg.containsVertex(s.getEnd())){
			    dg.addVertex(s.getEnd());}


			// add edges to create linking structure
			if(!dg.containsEdge(s.getStart(), s.getEnd()))
				dg.addEdge(s.getStart(), s.getEnd());

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
		System.out.println(dg.containsVertex(endPosition));
//		if(counter >= 2)
			shortestPathDijkstra();
//		counter++;
	}

	public void shortestPathDijkstra(){
		
		UndirectedGraph<Knot, DefaultEdge> ug =
	            new AsUndirectedGraph<Knot, DefaultEdge>(dg);
		DijkstraShortestPath<Knot, DefaultEdge> dsp = new DijkstraShortestPath<Knot, DefaultEdge>(ug, startPosition, endPosition);
		BellmanFordShortestPath<Knot, DefaultEdge> bfsp = new BellmanFordShortestPath<Knot, DefaultEdge>(ug, startPosition);
		System.out.println(bfsp.getCost(endPosition));
		System.out.println(dsp.getPathEdgeList());
		
		System.out.println("pathlength " + dsp.getPathLength());
	}

}
