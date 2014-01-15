package view.components;

import java.awt.Frame;

import javax.swing.JButton;

import control.Controller;
import dao.BaseDao;
/**
 * @author dimitri.haemmerli
 *
 */
/**
 * Generic Dialog for deleting saved models.
 * Shows a table containing the records available for deleting
 * and a Button to confirm the selection.
 *
 */
public class LoaderDialog extends GenericDialog {
	
	final static JButton btnLoad = new JButton("Load");
	/**
	 * 
	 * @param frame Root frame
	 * @param controller Reference to controller instance
	 * @param dao DAO implementation
	 * @param rowData Array containing the data in the table (First column must be the ID)
	 * @param columns Header captions, first item (ID) is not visible
	 */
	public LoaderDialog(Frame frame, final Controller controller, final BaseDao<?> dao, Object[][] rowData, String[] columns) {
		
		super(frame);
		
		super.setActionJB(btnLoad);

		super.createDialog(controller, dao, rowData, columns);	
		
	}
}
