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
 * Generic Dialog for loading saved models.
 * Shows a table containing the records available for loading
 * and a Button to confirm the selection.
 *
 */
public class LoaderDialog extends JDialog {
	
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
		
		final JButton btnLoad = new JButton("Load");
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			public void valueChanged(ListSelectionEvent event) {
				if(event.getValueIsAdjusting()) {
					return;
				}
				btnLoad.setEnabled(table.getSelectedRow() >= 0);
				
			}
		});
		scp.setBounds(5, 5, 340, 265);
		
		this.add(scp);
		
		btnLoad.setBounds(5, 275, 100, 30);
		btnLoad.setEnabled(false);
		this.add(btnLoad);
		
		btnLoad.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				int selectedRow = table.getSelectedRow();
				long id = (long)table.getModel().getValueAt(selectedRow, 0);
				
				controller.setModel(dao.getModelById(id));
				LoaderDialog.this.dispose();
				
			}
		});
	}
}
