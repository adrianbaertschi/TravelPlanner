package view;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.List;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.MapEditorModel;
import view.components.ReadOnlyJTable;
import control.MapEditorController;
import dao.MapEditorDao;

public class MapLoaderDialog extends JDialog {
	
	public MapLoaderDialog(Frame frame, final MapEditorController parentController) {
		super(frame);
		this.setLayout(null);
		this.setSize(350, 350);
		this.setModal(true);
		this.setTitle("Load Map");
		this.setLocationRelativeTo(frame);
		
		List<MapEditorModel> maps = MapEditorDao.getInstance().getMaps();
		
		String[] columns = new String[]{"ID",  "Name", "Streets", "Save Date"};
		Object[][] rowData = new Object[maps.size()][columns.length];
		for(int i = 0; i < maps.size(); i++) {
			rowData[i][0] = maps.get(i).getId();
			rowData[i][1] = maps.get(i).getName();
			rowData[i][2] = maps.get(i).getStreets().size();
			
			DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.GERMAN);
			rowData[i][3] = df.format(maps.get(i).getSaveDate().getTime());
		}
		final ReadOnlyJTable table = new ReadOnlyJTable(rowData, columns);
		
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(30);
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
		scp.setBounds(5, 5, 340, 265);
		
		this.add(scp);
		
		btnLoad.setBounds(5, 275, 100, 30);
		btnLoad.setEnabled(false);
		this.add(btnLoad);
		
		btnLoad.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				int selectedRow = table.getSelectedRow();
				long mapId = (long)table.getModel().getValueAt(selectedRow, 0);
				
				MapEditorModel modelById = MapEditorDao.getInstance().getModelById(mapId);
				parentController.setModel(modelById);
				
				MapLoaderDialog.this.setVisible(false);
				
			}
		});
	}

}
