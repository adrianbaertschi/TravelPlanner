/**
 * 
 */
package control;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

import control.MapEditorController.BtnLoadMapActioListener;
import control.MapEditorController.BtnResetActionListener;
import control.MapEditorController.BtnSaveMapActionListener;
import control.MapEditorController.MapMouseListener;
import dao.Database;
import model.FleetEditorModel;
import model.Knot;
import model.MapEditorModel;
import model.SimulationEditorModel;
import model.SolverMapGraph;
import model.Street;
import model.Vehicle;
import view.MapLoaderDialog;
import view.MasterGui;
import view.SimulationEditorView;

/**
 * @author dimitri.haemmerli
 *
 */
public class SimulationEditorController {
	
	private SimulationEditorView simulationEditorView;
	private SimulationEditorModel simulationEditorModel;
	private SolverMapGraph solverMapGraph;

	
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
//		sev.getBtnSaveMap().addActionListener(new BtnSaveMapActionListener());
//		sev.getBtnLoadMap().addActionListener(new BtnLoadMapActioListener());
//		sev.getBtnReset().addActionListener(new BtnResetActionListener());

	}
	
	public Component showView() {
		this.simulationEditorView.setVisible(true);
		return this.simulationEditorView;
	}
	


	class MapMouseListener implements MouseListener {

		private Street s;
		
		public void mouseClicked(MouseEvent e) {
			
			System.out.println("klicked");
			
			Knot knot = new Knot(e.getX(), e.getY());
			
			Knot selectedKnot = clickedOnEdge(knot);
			
			simulationEditorModel.getMapEditorModel().setSelectedKnot(selectedKnot);
			simulationEditorModel.changed();

			
	}
		private Knot clickedOnEdge(Knot k) {
			
			int x = k.getX();
			int y = k.getY();
			int toleranz = 5;
			
			Knot returnKnot = null;

			for(Street street : simulationEditorModel.getMapEditorModel().getStreets()) {
				// TODO: Refactor
				
				street.getStart().setColor(Color.DARK_GRAY);
				street.getEnd().setColor(Color.DARK_GRAY);
				// On Start Knoten?
				int xdiff = Math.abs(street.getStart().getX() - x);
				int ydiff = Math.abs(street.getStart().getY() - y);
				
				if(xdiff <= toleranz && ydiff <= toleranz) {
					returnKnot = street.getStart();
					street.getStart().setColor(Color.CYAN);
				}
				
				// On End?
				xdiff = Math.abs(street.getEnd().getX() - x);
				ydiff = Math.abs(street.getEnd().getY() - y);
				
				if(xdiff <= toleranz && ydiff <= toleranz) {
					returnKnot = street.getEnd();
					street.getEnd().setColor(Color.CYAN);
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

			for(Vehicle v : simulationEditorModel.getFleetEditorModel().getVehicles()){
				
				
				if(v.getStartKnot() != null && v.getFinishKnot() != null){
					
					
					
					solverMapGraph = new SolverMapGraph(simulationEditorModel);
					
				}
				
			}
		
		}
	}

}

