package view;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import model.entity.FleetEditorModel;
import model.entity.MapEditorModel;
import model.entity.SimulationEditorModel;
import control.FleetEditorController;
import control.MapEditorController;
import control.SimulationEditorController;

public class MasterGui extends JFrame {
	
	public MasterGui() {
		this.setSize(1200, 900);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		JTabbedPane tabPane = new JTabbedPane();
		
		MapEditorModel mapEditorModel = new MapEditorModel();
		MapEditorController mapEditorController = new MapEditorController(new MapEditorView(), mapEditorModel);
		
		FleetEditorModel fleetEditorModel = new FleetEditorModel();
		FleetEditorController fleetEditorController = new FleetEditorController(new FleetEditorView(), fleetEditorModel);
		
		
		SimulationEditorModel simulationEditorModel = new SimulationEditorModel();
		simulationEditorModel.setMapEditorModel(mapEditorModel);
		simulationEditorModel.setFleetEditorModel(fleetEditorModel);
		SimulationEditorController simulationEditorController = new SimulationEditorController(new SimulationEditorView(), simulationEditorModel);
		
		
		tabPane.addTab("Map Editor", mapEditorController.showView());
		tabPane.addTab("Fleet Editor", fleetEditorController.showView());
		tabPane.addTab("Simulation Editor", simulationEditorController.showView());

		
		this.setContentPane(tabPane);
	}

}
