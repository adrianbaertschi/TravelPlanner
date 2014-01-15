/**
 * 
 */
package view.components;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		
		JButton btnDelete = new JButton("Delete");
		super.setActionJB(btnDelete);
		
		btnDelete.setBounds(5, 275, 100, 30);
		btnDelete.setEnabled(false);
		this.add(btnDelete);

		super.createDialog(controller, dao, rowData, columns);
		
		btnDelete.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				int selectedRow = tableJT.getSelectedRow();
				long id = (long)tableJT.getModel().getValueAt(selectedRow, 0);
				dao.deleteModelById(id);
				
				DeleteDialog.this.dispose();
			}
		});
						
	}

}
