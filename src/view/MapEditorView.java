package view;

import static common.Constants.NODE_HEIGHT;
import static common.Constants.NODE_RADIUS;
import static common.Constants.NODE_WIDTH;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.text.NumberFormatter;

import model.MapEditorModel;
import model.Node;
import model.Street;
import model.StreetType;

public class MapEditorView extends JPanel implements Observer{
	
	private static final Color NODE_COLOR = 	Color.DARK_GRAY;
	private static final Color SELECTED_COLOR = Color.CYAN;
	
	private MapEditorModel model = new MapEditorModel();
	
	private JPanel mapArea;
	private JTextArea infoField;
	
	private JComboBox<StreetType> cbxStreetType;
	
	private JLabel lblNodeHeight;
	private JFormattedTextField txfNodeHeight;
	private JButton btnSetHeight;
	
	private JButton btnSaveMap;
	private JButton btnLoadMap;
	private JButton btnReset;
	private JButton btnDelete;

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
		
		lblNodeHeight = new JLabel("Node height:");
		lblNodeHeight.setBounds(920, 10, 100, 30);
		this.add(lblNodeHeight);
		
		NumberFormatter numberFormatter = new NumberFormatter(NumberFormat.getIntegerInstance(new Locale("de", "CH")));
		numberFormatter.setMinimum(0);
		numberFormatter.setMaximum(9999);
		numberFormatter.setCommitsOnValidEdit(true);
		
		txfNodeHeight = new JFormattedTextField(numberFormatter);
		txfNodeHeight.setBounds(1020, 10, 50, 30);
		this.add(txfNodeHeight);
		
		btnSetHeight = new JButton("set height");
		btnSetHeight.setBounds(1080, 10, 100, 30);
		btnSetHeight.setEnabled(false);
		this.add(btnSetHeight);
		
		// Delete Street
		btnDelete = new JButton("Delete selected street");
		btnDelete.setBounds(920, 280, 200, 30);
		btnDelete.setEnabled(false);
		this.add(btnDelete);
		
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
	
	public JComboBox<StreetType> getCbxStreetType() {
		return this.cbxStreetType;
	}
	
	public JFormattedTextField getTxfNodeHeight() {
		return this.txfNodeHeight;
	}
	
	public JButton getBtnSetHeight() {
		return this.btnSetHeight;
	}
	
	private void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		// antialiasing ON
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		for(Street street : model.getStreets()) {
			
			// Strassen
			if(street == model.getSelectedStreet()) {
				g2d.setColor(SELECTED_COLOR);
			} else {
				g2d.setColor(street.getStreetType().getColor());
			}
			g2d.setStroke(new BasicStroke(3));
			g2d.draw(convertStreetToLine(street));
			
			
			// Knoten
			g2d.setColor(NODE_COLOR);
			g2d.fill(convertNodeToEllipse(street.getStart()));
			g2d.fill(convertNodeToEllipse(street.getEnd()));
		}
		
		if(model.getSelectedKnot() != null) {
			g2d.setColor(SELECTED_COLOR);
			g2d.fill(convertNodeToEllipse(model.getSelectedKnot()));
		}
	}
	
	private Ellipse2D.Float convertNodeToEllipse(Node node) {
		return new Ellipse2D.Float(node.getX() - NODE_RADIUS, node.getY() - NODE_RADIUS, NODE_WIDTH, NODE_HEIGHT);
	}
	private Line2D.Float convertStreetToLine(Street s) {
		return new Line2D.Float(s.getStart().getX(), s.getStart().getY(), s.getEnd().getX(), s.getEnd().getY());
	}
	
	private void displayNodeInfo(Node selectedNode) {
		if(selectedNode != null) {
			infoField.setText("Selected Node:\nPosition: " + selectedNode.getX() + " / " + selectedNode.getY());
			txfNodeHeight.setValue(selectedNode.getHeight());
		} else {
			infoField.setText("");
		}
	}
	
	private void displayStreetInfo(Street st) {
		NumberFormat nf = NumberFormat.getIntegerInstance();
		String degrees = nf.format(Math.toDegrees(st.getIncline()));
		infoField.setText("Selected Street:\nLength: " + st.getLenth() + " \nIncline: " + degrees + "°");
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
		
		btnSetHeight.setEnabled(this.model.getSelectedKnot() != null);
	}
	
	public StreetType getSelectedStreetType() {
		return this.cbxStreetType.getItemAt(this.cbxStreetType.getSelectedIndex());
	}
}
