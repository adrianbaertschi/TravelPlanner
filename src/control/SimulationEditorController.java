/**
 * 
 */
package control;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import model.Node;
import model.SimulationEditorModel;
import model.SolverMapGraph;
import model.Street;
import model.Vehicle;
import view.SimulationEditorView;

/**
 * @author dimitri.haemmerli
 *
 */
public class SimulationEditorController {
	
	private SimulationEditorView simulationEditorView;
	private SimulationEditorModel simulationEditorModel;

	
	public SimulationEditorController(SimulationEditorView sev, SimulationEditorModel sem){
		
		this.simulationEditorView = sev;
		this.simulationEditorModel = sem;
		this.simulationEditorView.setMapEditorModel(simulationEditorModel.getMapEditorModel());
		this.simulationEditorModel.addObserver(simulationEditorView);
		addListener();

		
	}
	private void addListener() {
		simulationEditorView.getMapArea().addMouseListener(new MapMouseListener());
		simulationEditorView.getStartJB().addActionListener(new BtnSetStart());
		simulationEditorView.getFinishJB().addActionListener(new BtnSetFinish());
		simulationEditorView.getSimulationJB().addActionListener(new BtnSetSimulation());

	}
	
	public Component showView() {
		this.simulationEditorView.setVisible(true);
		return this.simulationEditorView;
	}
	


	class MapMouseListener implements MouseListener {

		public void mouseClicked(MouseEvent e) {
			
			System.out.println("klicked");
			
			Node knot = new Node(e.getX(), e.getY());
			
			Node selectedKnot = clickedOnNode(knot);
			
			simulationEditorModel.getMapEditorModel().setSelectedKnot(selectedKnot);
			simulationEditorModel.changed();

			
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

			for(Vehicle v : simulationEditorModel.getFleetEditorModel().getVehicles()){
				
				if(v.getIsSelected()){
					
					for(Street s : simulationEditorModel.getMapEditorModel().getStreets()){
						
						if(s.getStart().equals(simulationEditorModel.getMapEditorModel().getSelectedKnot())){
							
							v.setStartKnot(s.getStart());
							v.setCurrentKnot(s.getStart());
							simulationEditorModel.getMapEditorModel().setSelectedKnot(null);
							simulationEditorModel.changed();
							
						}						
						
						if(s.getEnd().equals(simulationEditorModel.getMapEditorModel().getSelectedKnot())){
							
							v.setStartKnot(s.getEnd());
							v.setCurrentKnot(s.getEnd());
							simulationEditorModel.getMapEditorModel().setSelectedKnot(null);
							simulationEditorModel.changed();

						}						
					}

				}
				
			}
			
		
		}
			

	}
	
	class BtnSetFinish implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			for(Vehicle v : simulationEditorModel.getFleetEditorModel().getVehicles()){
				
				if(v.getIsSelected()){
					
					for(Street s : simulationEditorModel.getMapEditorModel().getStreets()){
						
						if(s.getStart().equals(simulationEditorModel.getMapEditorModel().getSelectedKnot())){
							
							v.setFinishKnot(s.getStart());
							simulationEditorModel.getMapEditorModel().setSelectedKnot(null);
							simulationEditorModel.changed();
							
						}						
						
						if(s.getEnd().equals(simulationEditorModel.getMapEditorModel().getSelectedKnot())){
							
							v.setFinishKnot(s.getEnd());
							simulationEditorModel.getMapEditorModel().setSelectedKnot(null);
							simulationEditorModel.changed();

						}						
					}

				}
				
			}
			
		
		}
	}

	class BtnSetSimulation implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			for(Vehicle v : simulationEditorModel.getFleetEditorModel().getVehicles()) {
				
				
				if(v.getStartKnot() != null && v.getFinishKnot() != null){
					new SolverMapGraph(simulationEditorModel).startSimulation();;
				}
				
			}
		
		}
	}

}

