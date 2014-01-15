/**
 * 
 */
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
public class DeleteDialog extends GenericDialog {

	public final static JButton btnDelete = new JButton("Delete");
	/**
	 * 
	 * @param frame Root frame
	 * @param controller Reference to controller instance
	 * @param dao DAO implementation
	 * @param rowData Array containing the data in the table (First column must be the ID)
	 * @param columns Header captions, first item (ID) is not visible
	 */
	public DeleteDialog(Frame frame, final Controller controller, final BaseDao<?> dao, Object[][] rowData, String[] columns) {
		
		super(frame);
		
		super.setActionJB(btnDelete);

		super.createDialog(controller, dao, rowData, columns);
						
	}

}
