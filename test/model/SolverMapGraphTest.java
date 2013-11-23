package model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.junit.Before;
import org.junit.Test;

public class SolverMapGraphTest {
	SimulationEditorModel simulationEditorModel;
	
	@Before
	public void setUp() {
		simulationEditorModel = new SimulationEditorModel();

		// Fleet
		FleetEditorModel fleetEditorModel = new FleetEditorModel();
		
		Car car1 = new Car();
		car1.setSimulationOption(SimulationOption.SHORTEST_PATH);
		
		
		Car car2 = new Car();
		car2.setSimulationOption(SimulationOption.FASTEST_PATH);
		
		List<Vehicle> cars = new ArrayList<Vehicle>();
		cars.add(car1);
		cars.add(car2);
		fleetEditorModel.setVehicles(cars);
		
		simulationEditorModel.setFleetEditorModel(fleetEditorModel);
	
		
		// Map
		Node n1 = new Node(10, 10);
		Node n2 = new Node(100, 10);
		Node n3 = new Node(10, 100);
		Node n4 = new Node(100, 110);
		
		Street s1 = new Street(n1, n2);
		s1.setStreetType(StreetType.AUSSERORTS);
		Street s2 = new Street(n1, n3);
		s2.setStreetType(StreetType.INNERORTS);
		Street s3 = new Street(n2, n4);
		s3.setStreetType(StreetType.QUARTIER);
		Street s4 = new Street(n3, n4);
		s4.setStreetType(StreetType.AUTOBAHN);
		
		car1.setCurrentKnot(n1);
		car1.setStartKnot(n1);
		car1.setFinishKnot(n4);
		
		
		MapEditorModel mapEditorModel = new MapEditorModel();
		mapEditorModel.addStreet(s1);
		mapEditorModel.addStreet(s2);
		mapEditorModel.addStreet(s3);
		mapEditorModel.addStreet(s4);
		
		//TODO: creat map
		simulationEditorModel.setMapEditorModel(mapEditorModel);
		
	}
	
	
	@Test
	public void testCreateMapGraph() {
		SolverMapGraph solver = new SolverMapGraph(simulationEditorModel);
		solver.setVehicle(simulationEditorModel.getFleetEditorModel().getVehicles().get(0));
		
		SimpleWeightedGraph<Node,DefaultWeightedEdge> graph = solver.createMapGraph();
		
		assertEquals(4, graph.vertexSet().size());
		assertEquals(4, graph.edgeSet().size());
		
		System.out.println(graph);
		for(DefaultWeightedEdge e : graph.edgeSet()) {
			System.out.println(graph.getEdgeWeight(e));
		}
		
		// Close Street
		simulationEditorModel.getMapEditorModel().closeStreet(simulationEditorModel.getMapEditorModel().getStreets().get(0));
		
		graph = solver.createMapGraph();
		assertEquals(4, graph.vertexSet().size());
		assertEquals(3, graph.edgeSet().size());
		
		
		
	}
	
	@Test
	public void testGetPathForVehicle() {
		
		SolverMapGraph solver = new SolverMapGraph(simulationEditorModel);
		Vehicle v = simulationEditorModel.getFleetEditorModel().getVehicles().get(0);
		solver.setVehicle(v);
		Queue<Node> pathForVehicle = solver.getPathForVehicle(v, solver.createMapGraph());
		
		System.out.println(pathForVehicle);
		
		assertEquals(3, pathForVehicle.size());
		
	}

}
