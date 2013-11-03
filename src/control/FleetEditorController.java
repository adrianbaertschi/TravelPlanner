/**
 * 
 */
package control;

import java.awt.Component;

import control.SimulationEditorController.BtnSetFinish;
import control.SimulationEditorController.BtnSetSimulation;
import control.SimulationEditorController.BtnSetStart;
import control.SimulationEditorController.MapMouseListener;
import model.FleetEditorModel;
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
//		simulationEditorView.getMapArea().addMouseListener(new MapMouseListener());
//		simulationEditorView.getStartJB().addActionListener(new BtnSetStart());
//		simulationEditorView.getFinishJB().addActionListener(new BtnSetFinish());
//		simulationEditorView.getSimulationJB().addActionListener(new BtnSetSimulation());

	}
	
	public Component showView() {
		this.fleetEditorView.setVisible(true);
		return this.fleetEditorView;
	}


}
