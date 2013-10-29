/**
 * 
 */
package control;

import java.awt.Component;

import model.FleetEditorModel;
import view.FleetEditorView;

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
