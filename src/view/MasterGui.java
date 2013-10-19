package view;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import model.MapEditorModel;
import model.SimulationEditorModel;
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
		
		MapEditorModel mem = new MapEditorModel();
		MapEditorController mapEditorController = new MapEditorController(new MapEditorView(), mem);
		
		SimulationEditorModel sem = new SimulationEditorModel();
		sem.setMapEditorModel(mem);

		SimulationEditorController sec = new SimulationEditorController(new SimulationEditorView(), sem);
		
		
		tabPane.addTab("Map Editor", mapEditorController.showView());
		tabPane.addTab("Fleet Editor", new FleetEditorController().showView());
		tabPane.addTab("Simulation Editor", sec.showView());

		
		this.setContentPane(tabPane);
	}

}
