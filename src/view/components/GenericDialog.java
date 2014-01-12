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
 * Generic Dialog to access/manipulate saved models.
 * Shows a table containing the records available.
 * and a Button to confirm the selection.
 *
 */
public class GenericDialog extends JDialog {
	
	public JButton actionJB = new JButton();
	/**
	 * 
	 * @param frame Root frame
	 */
	public GenericDialog(Frame frame) {

		super(frame);
		this.setLayout(null);
		this.setSize(350, 350);
		this.setModal(true);
		this.setLocationRelativeTo(frame);	

	}
	/**
	 * 
	 * @param controller Reference to controller instance
	 * @param dao DAO implementation
	 * @param rowData Array containing the data in the table (First column must be the ID)
	 * @param columns Header captions, first item (ID) is not visible
	 */
	public void createDialog(final Controller controller, final BaseDao<?> dao, Object[][] rowData, String[] columns){
		
		
		final JTable tableJT = new JTable(rowData, columns) {
			@Override
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			};
		};
		tableJT.removeColumn(tableJT.getColumn(columns[0]));
		tableJT.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableJT.setFillsViewportHeight(true);
		JScrollPane scp = new JScrollPane(tableJT);
		
		tableJT.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			public void valueChanged(ListSelectionEvent event) {
				if(event.getValueIsAdjusting()) {
					return;
				}
				actionJB.setEnabled(tableJT.getSelectedRow() >= 0);
				
			}
		});
		scp.setBounds(5, 5, 340, 265);
		
		this.add(scp);

		actionJB.setBounds(5, 275, 100, 30);
		actionJB.setEnabled(false);
		this.add(actionJB);

		actionJB.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				int selectedRow = tableJT.getSelectedRow();
				long id = (long)tableJT.getModel().getValueAt(selectedRow, 0);
				
				if(actionJB.getText().equals("Delete")){
					
					dao.deleteModelById(id);
					
				}else if(actionJB.getText().equals("Load")){
					
					controller.setModel(dao.getModelById(id));

				}
				
				GenericDialog.this.dispose();

			}
		});
		
	}
	/**
	 * @return the actionJB
	 */
	public JButton getActionJB() {
		return actionJB;
	}
	/**
	 * @param actionJB the actionJB to set
	 */
	public void setActionJB(JButton actionJB) {
		this.actionJB = actionJB;
	}

	
}
