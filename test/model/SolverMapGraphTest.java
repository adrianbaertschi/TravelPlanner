package model;

import static org.junit.Assert.assertNotNull;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.junit.Before;
import org.junit.Test;

public class SolverMapGraphTest {
	SimulationEditorModel simulationEditorModel;
	
	@Before
	public void setUp() {
		simulationEditorModel = new SimulationEditorModel();

		FleetEditorModel fleetEditorModel = new FleetEditorModel();
		//TODO: add vehicles
		simulationEditorModel.setFleetEditorModel(fleetEditorModel);
	
		MapEditorModel mapEditorModel = new MapEditorModel();
		//TODO: creat map
		simulationEditorModel.setMapEditorModel(mapEditorModel);
		
	}
	
	
	@Test
	public void testCreateMapGraph() {
		SolverMapGraph solver = new SolverMapGraph(simulationEditorModel);
		
		SimpleWeightedGraph<Node,DefaultWeightedEdge> graph = solver.createMapGraph();
		assertNotNull(graph);
		
	}
	
	@Test
	public void testGetPathForVehicle() {
		
	}

}
