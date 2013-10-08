/**
 * 
 */
package control;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import control.MapEditorController.MapMouseListener;
import model.Car;
import model.FleetEditorModel;
import model.MapEditorModel;
import view.FleetEditorView;
import view.MapEditor;

/**
 * @author dimitri.haemmerli
 *
 */
public class FleetEditorController {

	
	
	private FleetEditorView view;
	private FleetEditorModel model;
	

	public FleetEditorController() {
		
		this.view = new FleetEditorView();
		this.model = new FleetEditorModel();
		
		addListener();
	}
	
	private void addListener() {
		view.getAddCarJB().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				model.addCar(new Car(view.getCarNameJTF().getText()));
				
			}
		});
	}

	public Component showView() {
		this.view.setVisible(true);
		return this.view;
	}


}
