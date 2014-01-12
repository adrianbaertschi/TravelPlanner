/**
 * 
 */
package view.components;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
public class DeleteDialog extends JDialog {

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
		this.setLayout(null);
		this.setSize(350, 350);
		this.setModal(true);
		this.setLocationRelativeTo(frame);
		
		final JTable table = new JTable(rowData, columns) {
			@Override
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			};
		};
		table.removeColumn(table.getColumn(columns[0]));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFillsViewportHeight(true);
		JScrollPane scp = new JScrollPane(table);
		
		final JButton btnDelete = new JButton("Delete");
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			public void valueChanged(ListSelectionEvent event) {
				if(event.getValueIsAdjusting()) {
					return;
				}
				btnDelete.setEnabled(table.getSelectedRow() >= 0);
				
			}
		});
		scp.setBounds(5, 5, 340, 265);
		
		this.add(scp);
		
		btnDelete.setBounds(5, 275, 100, 30);
		btnDelete.setEnabled(false);
		this.add(btnDelete);
		
		btnDelete.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				int selectedRow = table.getSelectedRow();
				long id = (long)table.getModel().getValueAt(selectedRow, 0);
				
				dao.deleteModelById(id);
				DeleteDialog.this.dispose();
				
			}
		});
	}

}
