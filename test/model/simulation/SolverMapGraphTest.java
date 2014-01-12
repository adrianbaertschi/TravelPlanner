package model.simulation;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import model.config.SimulationOption;
import model.config.StreetType;
import model.config.VehicleType;
import model.entity.Car;
import model.entity.FleetEditorModel;
import model.entity.MapEditorModel;
import model.entity.Node;
import model.entity.SimulationEditorModel;
import model.entity.Street;
import model.entity.Vehicle;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.junit.Before;
import org.junit.Test;

public class SolverMapGraphTest {
	SimulationEditorModel simulationEditorModel;
	private Street s1;
	private Street s2;
	private Street s3;
	private Street s4;
	
	private Node n1;
	private Node n2;
	private Node n3;
	private Node n4;
	
	@Before
	public void setUp() {
		simulationEditorModel = SimulationEditorModel.getInstance();

		// Fleet
		FleetEditorModel fleetEditorModel = new FleetEditorModel();
		
		Car car1 = new Car(VehicleType.BLUECAR);
		car1.setSimulationOption(SimulationOption.SHORTEST_PATH);
		
		
		Car car2 = new Car(VehicleType.GREENCAR);
		car2.setSimulationOption(SimulationOption.FASTEST_PATH);
		
		List<Vehicle> cars = new ArrayList<Vehicle>();
		cars.add(car1);
		cars.add(car2);
		fleetEditorModel.setVehicles(cars);
		
		simulationEditorModel.setFleetEditorModel(fleetEditorModel);
	
		
		// Map
		n1 = new Node(10, 10);
		n2 = new Node(100, 10);
		n3 = new Node(10, 100);
		n4 = new Node(80, 110);
		
		s1 = new Street(n1, n2);
		s1.setStreetType(StreetType.OUT_OF_TOWN);
		s2 = new Street(n1, n3);
		s2.setStreetType(StreetType.IN_TOWN);
		s3 = new Street(n4, n2);
		s3.setStreetType(StreetType.DISTRICT_ROAD);
		s4 = new Street(n4, n3);
		s4.setStreetType(StreetType.FREEWAY);
		
		car1.setCurrentNode(n1);
		car1.setStartNode(n1);
		car1.setFinishNode(n4);
		
		
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
		
		SimpleDirectedWeightedGraph<Node,DefaultWeightedEdge> graph = solver.createMapGraph();
		
		assertEquals(4, graph.vertexSet().size());
		assertEquals(8, graph.edgeSet().size());
		
		System.out.println(graph);
		for(DefaultWeightedEdge e : graph.edgeSet()) {
			System.out.println(graph.getEdgeWeight(e));
		}
		
		// Close Street
		simulationEditorModel.getMapEditorModel().closeStreet(s1);
		
		graph = solver.createMapGraph();
		assertEquals(4, graph.vertexSet().size());
		assertEquals(6, graph.edgeSet().size());
	}
	
	@Test
	public void testGetPathForVehicle() {
		
		SolverMapGraph solver = new SolverMapGraph(simulationEditorModel);
		Vehicle v = simulationEditorModel.getFleetEditorModel().getVehicles().get(0);
		solver.setVehicle(v);
		Queue<Node> pathForVehicle = solver.getPathForVehicle(v, solver.createMapGraph(), null);
		
		System.out.println(pathForVehicle);
		
		assertEquals(3, pathForVehicle.size());
		
		assertArrayEquals(new Node[]{n1, n3, n4}, pathForVehicle.toArray());
		
		s4.setOneWay(true);
		
		pathForVehicle = solver.getPathForVehicle(v, solver.createMapGraph(), null);
		assertArrayEquals(new Node[]{n1, n2, n4}, pathForVehicle.toArray());
		
	}

}
