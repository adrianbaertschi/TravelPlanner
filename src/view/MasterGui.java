package view;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import control.FleetEditorController;
import control.MapEditorController;

public class MasterGui extends JFrame {
	
	public MasterGui() {
		this.setSize(800, 600);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
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
		tabPane.addTab("Map Editor", new MapEditorController().showView());
		tabPane.addTab("Fleet Editor", new FleetEditorController().showView());
		tabPane.addTab("Simulation", new Simulation());
		
		this.setContentPane(tabPane);
	}

}
