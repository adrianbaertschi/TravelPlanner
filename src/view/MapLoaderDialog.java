package view;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.MapEditorModel;
import control.MapEditorController;
import dao.Database;

public class MapLoaderDialog extends JDialog {
	
	public MapLoaderDialog(Frame frame, final MapEditorController parentController) {
		super(frame);
		this.setLayout(null);
		this.setSize(500, 400);
		this.setModal(true);
		this.setLocationRelativeTo(frame);
		
		List<MapEditorModel> maps = Database.getInstance().getMaps();
		
		Object[][] rowData = new Object[maps.size()][2];
		for(int i = 0; i < maps.size(); i++) {
			rowData[i][0] = maps.get(i).getId();
			rowData[i][1] = maps.get(i).getName();
		}
		final JTable table = new JTable(rowData, new String[]{"ID",  "Name"}) { 
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			};
		};
		table.removeColumn(table.getColumn("ID"));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFillsViewportHeight(true);
		JScrollPane scp = new JScrollPane(table);
		
		final JButton btnLoad = new JButton("OK");
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			public void valueChanged(ListSelectionEvent event) {
				if(event.getValueIsAdjusting()) {
					return;
				}
				btnLoad.setEnabled(table.getSelectedRow() >= 0);
				
			}
		});
		scp.setBounds(5, 5, 300, 300);
		
		this.add(scp);
		
		btnLoad.setBounds(310, 5, 100, 30);
		btnLoad.setEnabled(false);
		this.add(btnLoad);
		
		btnLoad.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				int selectedRow = table.getSelectedRow();
				long mapId = (long)table.getModel().getValueAt(selectedRow, 0);
				
				MapEditorModel modelById = Database.getInstance().getModelById(mapId);
				parentController.setModel(modelById);
				
				MapLoaderDialog.this.setVisible(false);
				
			}
		});
	}

}
