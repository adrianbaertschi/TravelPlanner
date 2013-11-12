package view.components;

import javax.swing.JTable;

public class ReadOnlyJTable extends JTable {
	
	public ReadOnlyJTable(Object[][] rowData, String[] columns) {
		super(rowData, columns);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int colIndex) {
		return false;
	};

}
