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
import view.MapEditorView;

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
	}

	public Component showView() {
		this.view.setVisible(true);
		return this.view;
	}


}
