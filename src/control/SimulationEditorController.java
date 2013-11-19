/**
 * 
 */
package control;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import model.Node;
import model.SimulationEditorModel;
import model.SolverMapGraph;
import model.Street;
import model.UserDisruption;
import model.Vehicle;
import view.SimulationEditorView;

/**
 * @author dimitri.haemmerli
 *
 */
//TODO: implement coltroller
public class SimulationEditorController {
	
	private SimulationEditorView simulationEditorView;
	private SimulationEditorModel simulationEditorModel;
	private List<SolverMapGraph> solver = new ArrayList<SolverMapGraph>();

	
	public SimulationEditorController(SimulationEditorView sev, SimulationEditorModel sem){
		
		this.simulationEditorView = sev;
		this.simulationEditorModel = sem;
		this.simulationEditorView.setMapEditorModel(simulationEditorModel.getMapEditorModel());
		this.simulationEditorView.setFleetEditorModel(simulationEditorModel.getFleetEditorModel());
		this.simulationEditorModel.addObserver(simulationEditorView);
		addListener();

		
	}
	private void addListener() {
		simulationEditorView.getMapArea().addMouseListener(new MapMouseListener());
		simulationEditorView.getStartJB().addActionListener(new BtnSetStart());
		simulationEditorView.getFinishJB().addActionListener(new BtnSetFinish());
		simulationEditorView.getSimulationJB().addActionListener(new BtnSetSimulation());
		simulationEditorView.getNextVehicleJB().addActionListener(new BtnNextVehicle());
		simulationEditorView.getPreviousVehicleJB().addActionListener(new BtnPreviousVehicle());
		simulationEditorView.getCloseStreetJB().addActionListener(new BtnCloseStreet());
		simulationEditorView.getShortestPathJRB().addActionListener(new BtnShortestPath());
		simulationEditorView.getFastestPathJRB().addActionListener(new BtnFastestPath());


	}
	
	public Component showView() {
		this.simulationEditorView.setVisible(true);
		return this.simulationEditorView;
	}
	


	class MapMouseListener implements MouseListener {

		public void mouseClicked(MouseEvent e) {
			
			Node knot = new Node(e.getX(), e.getY());
			
			Node selectedKnot = clickedOnNode(knot);
			
			if(selectedKnot != null) {
				simulationEditorModel.getMapEditorModel().setSelectedStreet(null);
			} else {
				Street selectedStreet = clickedOnStreet(knot);
				simulationEditorModel.getMapEditorModel().setSelectedStreet(selectedStreet);
			}
			
			simulationEditorModel.getMapEditorModel().setSelectedKnot(selectedKnot);
			simulationEditorModel.changed(null);
			
			
	}
		private Node clickedOnNode(Node k) {
			
			int x = k.getX();
			int y = k.getY();
			int toleranz = 5;
			
			Node returnKnot = null;

			for(Street street : simulationEditorModel.getMapEditorModel().getStreets()) {
				
				
				// On Start Knoten?
				int xdiff = Math.abs(street.getStart().getX() - x);
				int ydiff = Math.abs(street.getStart().getY() - y);
				
				if(xdiff <= toleranz && ydiff <= toleranz) {
					returnKnot = street.getStart();
				}
				
				// On End?
				xdiff = Math.abs(street.getEnd().getX() - x);
				ydiff = Math.abs(street.getEnd().getY() - y);
				
				if(xdiff <= toleranz && ydiff <= toleranz) {
					returnKnot = street.getEnd();
				}
			}
			
			return returnKnot;
		}
		
		private Street clickedOnStreet(Node point) {
			for(Street street : simulationEditorModel.getMapEditorModel().getStreets()) {
				if(street.isPointOnStreet(point.getX(), point.getY())) {
					return street;
				}
			}
			return null;
		}



		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

	}
	class BtnSetStart implements ActionListener {

		public void actionPerformed(ActionEvent e) {

								
					for(Street s : simulationEditorModel.getMapEditorModel().getStreets()){
						
						if(s.getStart().equals(simulationEditorModel.getMapEditorModel().getSelectedKnot())){
							
							simulationEditorModel.getFleetEditorModel().getVehicles().get(simulationEditorModel.getFleetEditorModel().getVehiclePos()).setStartKnot(s.getStart());
							simulationEditorModel.getFleetEditorModel().getVehicles().get(simulationEditorModel.getFleetEditorModel().getVehiclePos()).setCurrentKnot(s.getStart());
							simulationEditorModel.getMapEditorModel().setSelectedKnot(null);
							simulationEditorModel.changed(null);
							
						}						
						
						if(s.getEnd().equals(simulationEditorModel.getMapEditorModel().getSelectedKnot())){
							
							simulationEditorModel.getFleetEditorModel().getVehicles().get(simulationEditorModel.getFleetEditorModel().getVehiclePos()).setStartKnot(s.getEnd());
							simulationEditorModel.getFleetEditorModel().getVehicles().get(simulationEditorModel.getFleetEditorModel().getVehiclePos()).setCurrentKnot(s.getEnd());
							simulationEditorModel.getMapEditorModel().setSelectedKnot(null);
							simulationEditorModel.changed(null);

						}						
					}

				}
					
			

	}
	
	class BtnSetFinish implements ActionListener {

		public void actionPerformed(ActionEvent e) {

									
					for(Street s : simulationEditorModel.getMapEditorModel().getStreets()){
						
						if(s.getStart().equals(simulationEditorModel.getMapEditorModel().getSelectedKnot())){
							
							simulationEditorModel.getFleetEditorModel().getVehicles().get(simulationEditorModel.getFleetEditorModel().getVehiclePos()).setFinishKnot(s.getStart());
							simulationEditorModel.getMapEditorModel().setSelectedKnot(null);
							simulationEditorModel.changed(null);
							
						}						
						
						if(s.getEnd().equals(simulationEditorModel.getMapEditorModel().getSelectedKnot())){
							
							simulationEditorModel.getFleetEditorModel().getVehicles().get(simulationEditorModel.getFleetEditorModel().getVehiclePos()).setFinishKnot(s.getEnd());
							simulationEditorModel.getMapEditorModel().setSelectedKnot(null);
							simulationEditorModel.changed(null);

						}						
	

				
			}
			
		
		}
	}

	class BtnSetSimulation implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			simulationEditorView.setInSimulation(true);
			for(int i = 0; i< simulationEditorModel.getFleetEditorModel().getVehicles().size(); i++){
				
				SolverMapGraph s = new SolverMapGraph(simulationEditorModel);
				
				simulationEditorModel.addObserver(s);
				solver.add(s);
				solver.get(i).setVehicle(simulationEditorModel.getFleetEditorModel().getVehicles().get(i));
				solver.get(i).getVehicle().setThread(new Thread(solver.get(i)));
			}
			
			for(SolverMapGraph smg: solver) {
				
				if(smg.getVehicle().getStartKnot() != null && smg.getVehicle().getFinishKnot() != null){
					
//					SolverMapGraph smg = new SolverMapGraph(simulationEditorModel);
//					v.setThread(new Thread(new SolverMapGraph(simulationEditorModel)));
					smg.getVehicle().getThread().start();
//					new SolverMapGraph(simulationEditorModel).startSimulation();;
				}
				
			}
			
			
		
		}
	}

	class BtnNextVehicle implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			simulationEditorModel.getFleetEditorModel().increaseVehiclePos();
			simulationEditorModel.changed(null);
		
		}
	}
	class BtnPreviousVehicle implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			simulationEditorModel.getFleetEditorModel().decreaseVehiclePos();
			simulationEditorModel.changed(null);
		}
	}
	
	class BtnCloseStreet implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			Street selectedStreet = simulationEditorModel.getMapEditorModel().getSelectedStreet();
			boolean isVehicleOnStreet = false;
			
			for(Vehicle vehicle : simulationEditorModel.getFleetEditorModel().getVehicles()) {
				if(selectedStreet.getStart().equals(vehicle.getCurrentKnot()) && selectedStreet.getEnd().equals(vehicle.getNextKnot()) ||
						selectedStreet.getStart().equals(vehicle.getNextKnot()) && selectedStreet.getEnd().equals(vehicle.getCurrentKnot()))
					
					isVehicleOnStreet = true;
			}
			
			if(!isVehicleOnStreet) {
				simulationEditorModel.getMapEditorModel().closeStreet(selectedStreet);
//				simulationEditorModel.getMapEditorModel().setSelectedStreet(null);
				simulationEditorModel.changed(new UserDisruption());
			}
			
		}
	}
		
	class BtnShortestPath implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			simulationEditorModel.getFleetEditorModel().getVehicles().get(simulationEditorModel.getFleetEditorModel().getVehiclePos()).setSimulationOption(1);;
			simulationEditorModel.changed(null);
		}
	}
	class BtnFastestPath implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			simulationEditorModel.getFleetEditorModel().getVehicles().get(simulationEditorModel.getFleetEditorModel().getVehiclePos()).setSimulationOption(2);;
			simulationEditorModel.changed(null);
		}
	}	
	

}

