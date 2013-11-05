/**
 * 
 */
package control;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import control.SimulationEditorController.BtnSetFinish;
import control.SimulationEditorController.BtnSetSimulation;
import control.SimulationEditorController.BtnSetStart;
import control.SimulationEditorController.MapMouseListener;
import model.FleetEditorModel;
import model.SolverMapGraph;
import model.Vehicle;
import view.FleetEditorView;

/**
 * @author dimitri.haemmerli
 *
 */
public class FleetEditorController {


	private FleetEditorView fleetEditorView;
	private FleetEditorModel fleetEditorModel;
	

	public FleetEditorController(FleetEditorView view, FleetEditorModel model ) {
		
		this.fleetEditorView = view;
		this.fleetEditorModel = model;
		this.fleetEditorModel.addObserver(fleetEditorView);
		addListener();

	}

	private void addListener() {
		fleetEditorView.getNextFleetVehicleJB().addActionListener(new BtnNextFleetVehicle());
		fleetEditorView.getPreviousFleetVehicleJB().addActionListener(new BtnPreviousFleetVehicle());
		fleetEditorView.getNextVehicleJB().addActionListener(new BtnNextVehicle());
		fleetEditorView.getPreviousVehicleJB().addActionListener(new BtnPreviousVehicle());
		fleetEditorView.getAddVehicleJB().addActionListener(new BtnAddVehicle());
//		simulationEditorView.getFinishJB().addActionListener(new BtnSetFinish());
//		simulationEditorView.getSimulationJB().addActionListener(new BtnSetSimulation());

	}
	
	public Component showView() {
		this.fleetEditorView.setVisible(true);
		return this.fleetEditorView;
	}

	class BtnNextFleetVehicle implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			fleetEditorModel.increaseVehiclePos();
		
		}
	}
	class BtnPreviousFleetVehicle implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			fleetEditorModel.decreaseVehiclePos();
		
		}
	}

	class BtnNextVehicle implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			fleetEditorModel.increaseVehicleSelectionPos();
		
		}
	}
	class BtnPreviousVehicle implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			fleetEditorModel.decreaseVehicleSelectionPos();

		}
	}
	class BtnAddVehicle implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			fleetEditorModel.addVehicle(fleetEditorModel.getVehicleSelection().get(fleetEditorModel.getVehicleSelectionPos()));
		}
	}

	
}
