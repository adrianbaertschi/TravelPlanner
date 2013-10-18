package view;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.MapEditorModel;
import control.MapEditorController;
import dao.Database;

public class MapLoaderDialog extends JDialog {
	
	private MapEditorController parentController;
	
	public MapLoaderDialog(Frame frame, final MapEditorController parentController) {
		super(frame);
		this.setLayout(null);
		this.setSize(500, 400);
		this.setModal(true);
		this.setLocationRelativeTo(frame);
		
		this.parentController = parentController;

		
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
		
		table.setFillsViewportHeight(true);
		JScrollPane scp = new JScrollPane(table);
		scp.setBounds(5, 5, 300, 300);
		
		this.add(scp);
		
		JButton btnLoad = new JButton("OK");
		btnLoad.setBounds(310, 5, 100, 30);
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
