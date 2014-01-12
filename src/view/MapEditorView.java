package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.text.NumberFormatter;

import model.config.StreetType;
import model.entity.MapEditorModel;
import model.entity.Node;
import model.entity.Street;

public class MapEditorView extends JPanel implements Observer{
	
	private MapEditorModel model = new MapEditorModel();
	
	private JPanel mapArea;
	private JTextArea infoField;
	
	private JComboBox<StreetType> cbxStreetType;
	
	private JLabel lblNodeHeight;
	private JFormattedTextField txfNodeHeight;
	private JButton btnSetHeight;

	private JButton btnDelete;
	private JCheckBox chxOneWay;
	private JCheckBox chxNoPassing;
	
	private JButton btnSaveMap;
	private JButton btnLoadMap;
	private JButton btnReset;
	private JButton deleteMapJB;

	public MapEditorView() {
		
		// Map Area
		mapArea = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				draw(g);
			}
		};
		this.setLayout(null);
		mapArea.setBounds(10, 10, 900, 800);
		mapArea.setBackground(Color.WHITE);
		mapArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		
		this.add(mapArea);
		
		// Details
		infoField = new JTextArea();
		infoField.setBounds(920, 100, 260, 100);
		infoField.setEditable(false);
		
		this.add(infoField);
		
		cbxStreetType = new JComboBox<StreetType>(StreetType.values());
		cbxStreetType.setBounds(920, 240, 200, 30);
		this.add(cbxStreetType);
		
		lblNodeHeight = new JLabel("Node height (km):");
		lblNodeHeight.setBounds(920, 10, 140, 30);
		this.add(lblNodeHeight);
		
		NumberFormatter numberFormatter = new NumberFormatter(NumberFormat.getIntegerInstance(new Locale("de", "CH")));
		numberFormatter.setMinimum(0);
		numberFormatter.setMaximum(10);
		numberFormatter.setCommitsOnValidEdit(true);
		
		txfNodeHeight = new JFormattedTextField(numberFormatter);
		txfNodeHeight.setBounds(1050, 10, 60, 30);
		this.add(txfNodeHeight);
		
		btnSetHeight = new JButton("Set");
		btnSetHeight.setBounds(1120, 10, 60, 30);
		btnSetHeight.setEnabled(false);
		this.add(btnSetHeight);
		
		// Delete Street
		btnDelete = new JButton("Delete selected street");
		btnDelete.setBounds(920, 330, 200, 30);
		btnDelete.setEnabled(false);
		this.add(btnDelete);
		
		chxOneWay = new JCheckBox("One Way");
		chxOneWay.setBounds(920, 270, 200, 30);
		this.add(chxOneWay);
		
		chxNoPassing = new JCheckBox("No passing");
		chxNoPassing.setBounds(920, 300, 200, 30);
		this.add(chxNoPassing);
		
		// Save Button
		btnSaveMap = new JButton("Save Map");
		btnSaveMap.setBounds(920, 740, 130, 30);
		this.add(btnSaveMap);
		
		// Load Button
		btnLoadMap = new JButton("Load Map");
		btnLoadMap.setBounds(1055, 740, 130, 30);
		this.add(btnLoadMap);
		
		// Reset Map
		btnReset = new JButton("Reset Map");
		btnReset.setBounds(920, 780, 130, 30);
		this.add(btnReset);
		
		deleteMapJB = new JButton("Delete");
		deleteMapJB.setBounds(1055, 780, 130, 30);
		this.add(deleteMapJB);

	}
	
	public JPanel getMapArea() {
		return this.mapArea;
	}
	
	public JButton getBtnSaveMap() {
		return btnSaveMap;
	}
	
	public JButton getBtnLoadMap() {
		return btnLoadMap;
	}
	
	public JButton getBtnReset() {
		return btnReset;
	}
	
	public JButton getBtnDelete() {
		return btnDelete;
	}
	
	/**
	 * @return the deleteMapJB
	 */
	public JButton getDeleteMapJB() {
		return deleteMapJB;
	}

	/**
	 * @param deleteMapJB the deleteMapJB to set
	 */
	public void setDeleteMapJB(JButton deleteMapJB) {
		this.deleteMapJB = deleteMapJB;
	}

	public JComboBox<StreetType> getCbxStreetType() {
		return this.cbxStreetType;
	}
	
	public JFormattedTextField getTxfNodeHeight() {
		return this.txfNodeHeight;
	}
	
	public JButton getBtnSetHeight() {
		return this.btnSetHeight;
	}
	
	public JCheckBox getChxOneWay() {
		return this.chxOneWay;
	}
	
	public JCheckBox getChxNoPassing() {
		return this.chxNoPassing;
	}
	
	private void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		DrawingUtil.drawMap(g2d, model);
	}
	
	private void displayNodeInfo(Node selectedNode) {
		if(selectedNode != null) {
			infoField.setText("Selected Node:\nPosition: " + selectedNode.getX() + "/" + selectedNode.getY() + "\nheight: " + selectedNode.getHeight() + " km");
			txfNodeHeight.setValue(selectedNode.getHeight());
		} else {
			infoField.setText("");
		}
	}
	
	private void displayStreetInfo(Street st) {
		NumberFormat nf = NumberFormat.getIntegerInstance();
		String degrees = nf.format(Math.toDegrees(st.getIncline()));
		infoField.setText("Selected Street:\nLength: " + st.getLenth() + " km \nIncline: " + degrees + "°");
	}

	public void update(Observable model, Object value) {
		if(model instanceof MapEditorModel) {
			this.model = (MapEditorModel) model;
			repaint();
			infoField.setText("");
			txfNodeHeight.setValue(0);
		}
		if(value instanceof Street) {
			if(this.model.getSelectedStreet() == value) {
				displayStreetInfo((Street) value);
				btnDelete.setEnabled(true);
			} else {
				infoField.setText("");
				btnDelete.setEnabled(false);
			}
		}
		if(value instanceof Node) {
			displayNodeInfo((Node)value);
			btnDelete.setEnabled(false);
		}
		
		btnSetHeight.setEnabled(this.model.getSelectedNode() != null);
	}
	
	public StreetType getSelectedStreetType() {
		return this.cbxStreetType.getItemAt(this.cbxStreetType.getSelectedIndex());
	}
}
